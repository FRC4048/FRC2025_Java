package frc.robot.subsystems.swervev3.estimation;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.utils.RobotMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.LoggableSystem;
import frc.robot.utils.math.ArrayUtils;
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
  private final SwerveModule backRight;;

  /* standard deviation of robot states, the lower the numbers arm, the more we trust odometry */
  private static final Vector<N3> stateStdDevs1 = VecBuilder.fill(0.075, 0.075, 0.001);

  /* standard deviation of vision readings, the lower the numbers arm, the more we trust vision */
  private static final Vector<N3> visionMeasurementStdDevs1 = VecBuilder.fill(0.5, 0.5, 0.5);

  /* standard deviation of vision readings, the lower the numbers arm, the more we trust vision */
  private static final Vector<N3> visionMeasurementStdDevs2 = VecBuilder.fill(0.45, 0.45, 0.001);
  private static final Transform2d cameraOneTransform =
      new Transform2d(
          Constants.CAMERA_OFFSET_FROM_CENTER_X,
          Constants.CAMERA_OFFSET_FROM_CENTER_Y,
          new Rotation2d());
  private static final Transform2d cameraTwoTransform =
      new Transform2d(
          Constants.CAMERA_OFFSET_FROM_CENTER_X,
          Constants.CAMERA_OFFSET_FROM_CENTER_Y,
          new Rotation2d());
  private final PoseManager poseManager;
  private int invalidApriltagNum;

  public PoseEstimator(
      SwerveModule frontLeftMotor,
      SwerveModule frontRightMotor,
      SwerveModule backLeftMotor,
      SwerveModule backRightMotor,
      SwerveDriveKinematics kinematics,
      double initGyroValueDeg) {
    this.frontLeft = frontLeftMotor;
    this.frontRight = frontRightMotor;
    this.backLeft = backLeftMotor;
    this.backRight = backRightMotor;
    invalidApriltagNum = 0;
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
            visionMeasurementStdDevs1,
            kinematics,
            initMeasurement,
            m1Buffer);
    SmartDashboard.putData(field);
  }


  /**
   * updates odometry, should be called in periodic
   *
   * @see SwerveDrivePoseEstimator#update(Rotation2d, SwerveModulePosition[])
   */
  public void updatePosition(OdometryMeasurement m) {
    if (DriverStation.isEnabled()) {
      // poseManager.addOdomMeasurement(m, Logger.getRealTimestamp());
      poseManager.addOdomMeasurement(m, Logger.getTimestamp());
    }
    field.setRobotPose(poseManager.getEstimatedPosition());
  }

  private boolean validAprilTagPose(double[] measurement) {
    return !ArrayUtils.allMatch(measurement, -1.0) && measurement.length == 3;
  }

  /**
   * Collects Apriltag measurement(s) from the IO and checks their validity. If they are valid they
   * are sent to the {@link PoseManager} for further processing
   */


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
}