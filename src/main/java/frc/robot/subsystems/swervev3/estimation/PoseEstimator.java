package frc.robot.subsystems.swervev3.estimation;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.apriltags.ApriltagInputs;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.subsystems.swervev3.vision.BasicVisionFilter;
import frc.robot.subsystems.swervev3.vision.SquareVisionTruster;
import frc.robot.utils.Apriltag;
import frc.robot.utils.RobotMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.math.ArrayUtils;
import java.util.Arrays;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

/**
 * Class in charge of feeding odometry and apriltag measurements from their respective IOs into a
 * {@link PoseManager} which outputs a robot position
 */
public class PoseEstimator {
  private final Field2d field = new Field2d();
  private final SwerveModule frontLeft;
  private final SwerveModule frontRight;
  private final SwerveModule backLeft;
  private final SwerveModule backRight;
  private final LoggableSystem<LoggableIO<ApriltagInputs>, ApriltagInputs> apriltagSystem;
  private int invalidCounter = 0;

  /* standard deviation of robot states, the lower the numbers arm, the more we trust odometry */
  public static final Vector<N3> stateStdDevs1 = VecBuilder.fill(0.075, 0.075, 0.001);

  /* standard deviation of vision readings, the lower the numbers arm, the more we trust vision */
  //  private static final Vector<N3> visionMeasurementStdDevs1 = VecBuilder.fill(0.5, 0.5, 0.5);

  /* the rate at which variance of vision measurements increases as distance from the tag increases*/
  private static final double visionStdRateOfChange = 1;

  /* standard deviation of vision readings, the lower the numbers arm, the more we trust vision */
  public static final Vector<N3> visionMeasurementStdDevs2 = VecBuilder.fill(0.3, 0.3, 100);
  private final FilterablePoseManager poseManager;

  public PoseEstimator(
      SwerveModule frontLeftMotor,
      SwerveModule frontRightMotor,
      SwerveModule backLeftMotor,
      SwerveModule backRightMotor,
      LoggableIO<ApriltagInputs> apriltagIO,
      SwerveDriveKinematics kinematics,
      double initGyroValueDeg) {
    this.frontLeft = frontLeftMotor;
    this.frontRight = frontRightMotor;
    this.backLeft = backLeftMotor;
    this.backRight = backRightMotor;
    this.apriltagSystem = new LoggableSystem<>(apriltagIO, new ApriltagInputs("Apriltag"));
    OdometryMeasurement initMeasurement =
        new OdometryMeasurement(
            new SwerveModulePosition[] {
              frontLeft.getPosition(),
              frontRight.getPosition(),
              backLeft.getPosition(),
              backRight.getPosition(),
            },
            initGyroValueDeg);
    TimeInterpolatableBuffer<Pose2d> m1Buffer =
        TimeInterpolatableBuffer.createBuffer(Constants.POSE_BUFFER_STORAGE_TIME);
    this.poseManager =
        new FilterablePoseManager(
            stateStdDevs1,
            visionMeasurementStdDevs2,
            kinematics,
            initMeasurement,
            m1Buffer,
            new BasicVisionFilter(m1Buffer) {
              @Override
              public Pose2d getVisionPose(VisionMeasurement measurement) {
                return measurement.measurement();
              }
            },
            new SquareVisionTruster(visionMeasurementStdDevs2, 0.4));
    SmartDashboard.putData(field);
  }

  public void updateInputs() {
    apriltagSystem.updateInputs();
  }

  /**
   * updates odometry, should be called in periodic
   *
   * @see SwerveDrivePoseEstimator#update(Rotation2d, SwerveModulePosition[])
   */
  public void updatePosition(OdometryMeasurement m) {
    if (!Robot.getMode().equals(RobotMode.DISABLED)) {
      poseManager.addOdomMeasurement(m, Logger.getTimestamp());
    }
    field.setRobotPose(poseManager.getEstimatedPosition());
  }

  private boolean validAprilTagPose(double[] measurement) {
    return !ArrayUtils.allMatch(measurement, -1.0) && measurement.length == 3;
  }

  private void updateVision(int... invalidApriltagNumbers) {
    long start = System.currentTimeMillis();
    if (Constants.ENABLE_VISION && Robot.getMode() != RobotMode.DISABLED) {
      for (int i = 0; i < apriltagSystem.getInputs().timestamp.length; i++) {
        double[] pos =
            new double[] {
              apriltagSystem.getInputs().posX[i],
              apriltagSystem.getInputs().posY[i],
              apriltagSystem.getInputs().poseYaw[i]
            };
        if (validAprilTagPose(pos)
            && !ArrayUtils.contains(
                invalidApriltagNumbers, apriltagSystem.getInputs().apriltagNumber[i])) {
          VisionMeasurement measurement = getVisionMeasurement(pos, i);
          poseManager.registerVisionMeasurement(measurement);
        } else {
          invalidCounter++;
          Logger.recordOutput("Apriltag/ValidationFailureCount", invalidCounter);
        }
      }
    }
      long end = System.currentTimeMillis();
      Logger.recordOutput("RegisteringVisionTimeMillis", end - start);
    poseManager.processQueue();
  }

  private VisionMeasurement getVisionMeasurement(double[] pos, int index) {
    double serverTime = apriltagSystem.getInputs().serverTime[index];
    double timestamp = 0; // latency is not right we are assuming zero
    double latencyInSec = (serverTime - timestamp) / 1000;
    Pose2d visionPose = new Pose2d(pos[0], pos[1], getEstimatedPose().getRotation());
    double distanceFromTag = apriltagSystem.getInputs().distanceToTag[index];
    return new VisionMeasurement(visionPose, distanceFromTag, latencyInSec);
  }

  /**
   * Collects Apriltag measurement(s) from the IO and checks their validity. If they are valid they
   * are sent to the {@link PoseManager} for further processing
   */
  public void updateVision() {
    updateVision(15, 4, 14, 5, 16, 3);
  }

  public void updateVision(Apriltag focusedTag) {
    int[] invalidTags =
        Arrays.stream(Apriltag.values())
            .filter(a -> a != focusedTag)
            .mapToInt(Apriltag::number)
            .toArray();
    updateVision(invalidTags);
  }

  /**
   * @param radians robot angle to reset odometry to
   * @param translation2d robot field position to reset odometry to
   * @see SwerveDrivePoseEstimator#resetPosition(Rotation2d, SwerveModulePosition[], Pose2d)
   */
  public void resetOdometry(double radians, Translation2d translation2d) {
    OdometryMeasurement initMeasurement =
        new OdometryMeasurement(
            new SwerveModulePosition[] {
              frontLeft.getPosition(),
              frontRight.getPosition(),
              backLeft.getPosition(),
              backRight.getPosition(),
            },
            (radians * 180) / Math.PI);
    poseManager.resetPose(initMeasurement, translation2d);
    field.setRobotPose(poseManager.getEstimatedPosition());
  }

  @AutoLogOutput
  public Pose2d getEstimatedPose() {
    return poseManager.getEstimatedPosition();
  }

  public Field2d getField() {
    return field;
  }

  public FilterablePoseManager getPoseManager() {
    return poseManager;
  }

  public void addMockVisionMeasurement() {
    poseManager.registerVisionMeasurement(
        new VisionMeasurement(getEstimatedPose(), 0, Logger.getTimestamp() / 1e6));
  }
}
