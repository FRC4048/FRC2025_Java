package frc.robot.subsystems.swervev3;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.apriltags.ApriltagInputs;
import frc.robot.constants.Constants;
import frc.robot.subsystems.gyro.GyroIO;
import frc.robot.subsystems.gyro.GyroInputs;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.estimation.PoseEstimator;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.subsystems.swervev3.vision.DistanceVisionTruster;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.shuffleboard.SmartShuffleboard;
import java.util.Collections;
import org.littletonrobotics.junction.Logger;

public class SwerveDrivetrain extends SubsystemBase {
  private final SwerveModule frontLeft;
  private final SwerveModule frontRight;
  private final SwerveModule backLeft;
  private final SwerveModule backRight;
  private final Translation2d frontLeftLocation =
      new Translation2d(Constants.DRIVE_BASE_WIDTH / 2, Constants.DRIVE_BASE_LENGTH / 2);
  private final Translation2d frontRightLocation =
      new Translation2d(Constants.DRIVE_BASE_WIDTH / 2, -Constants.DRIVE_BASE_LENGTH / 2);
  private final Translation2d backLeftLocation =
      new Translation2d(-Constants.DRIVE_BASE_WIDTH / 2, Constants.DRIVE_BASE_LENGTH / 2);
  private final Translation2d backRightLocation =
      new Translation2d(-Constants.DRIVE_BASE_WIDTH / 2, -Constants.DRIVE_BASE_LENGTH / 2);
  private final SwerveDriveKinematics kinematics =
      new SwerveDriveKinematics(
          frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);
  private final LoggableSystem<GyroIO, GyroInputs> gyroSystem;
  private DriveMode driveMode = DriveMode.FIELD_CENTRIC;
  private final PoseEstimator poseEstimator;
  private boolean facingTarget = false;
  private TrajectoryConfig trajectoryConfig;
  // controller will add an additional meter per second in the x direction for every meter of error
  // in the x direction
  private final HolonomicDriveController finePathController =
      new HolonomicDriveController(
          new PIDController(1, 0, 0),
          new PIDController(1, 0, 0),
          new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14)));

  public SwerveDrivetrain(
      SwerveModule frontLeftModule,
      SwerveModule frontRightModule,
      SwerveModule backLeftModule,
      SwerveModule backRightModule,
      GyroIO gyroIO,
      LoggableIO<ApriltagInputs> apriltagIO) {
    this.frontLeft = frontLeftModule;
    this.frontRight = frontRightModule;
    this.backLeft = backLeftModule;
    this.backRight = backRightModule;
    this.gyroSystem = new LoggableSystem<>(gyroIO, new GyroInputs("Gyro"));
    this.poseEstimator =
        new PoseEstimator(
            frontLeft, frontRight, backLeft, backRight, apriltagIO, kinematics, getLastGyro());
    finePathController.setTolerance(new Pose2d(0.025, 0.025, Rotation2d.fromDegrees(5)));
    trajectoryConfig = new TrajectoryConfig(0.25, 0.25);

    RobotConfig config = null;
    try {
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      // Handle exception as needed
      e.printStackTrace();
    }

    AutoBuilder.configure(
        this::getPose,
        this::resetOdometry,
        this::getChassisSpeeds,
        this::drive,
        new PPHolonomicDriveController(
            new PIDConstants(
                Constants.PATH_PLANNER_TRANSLATION_PID_P,
                Constants.PATH_PLANNER_TRANSLATION_PID_I,
                Constants.PATH_PLANNER_TRANSLATION_PID_D), // Translation PID constants
            new PIDConstants(
                Constants.PATH_PLANNER_ROTATION_PID_P,
                Constants.PATH_PLANNER_ROTATION_PID_I,
                Constants.PATH_PLANNER_ROTATION_PID_D) // Rotation PID constants
            ),
        config,
        () -> {
          // Boolean supplier that controls when the path will be mirrored for the red alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this);
  }

  @Override
  public void periodic() {
    poseEstimator.updateInputs();
    processInputs();
    OdometryMeasurement odom =
        new OdometryMeasurement(
            new SwerveModulePosition[] {
              frontLeft.getPosition(),
              frontRight.getPosition(),
              backLeft.getPosition(),
              backRight.getPosition()
            },
            getLastGyro());
    Logger.recordOutput("LastOdomModPoses", odom.modulePosition());
    poseEstimator.updatePosition(odom);
    poseEstimator.updateVision();
    Logger.recordOutput(
        "realSwerveStates",
        frontLeft.getLatestState(),
        frontRight.getLatestState(),
        backLeft.getLatestState(),
        backRight.getLatestState());
    Logger.recordOutput("EstimatedX", getPose().getX());
    Logger.recordOutput("EstimatedY", getPose().getY());
    Logger.recordOutput("EstimatedYaw", getPose().getRotation().getDegrees());
  }

  private void processInputs() {
    frontLeft.updateInputs();
    frontRight.updateInputs();
    backLeft.updateInputs();
    backRight.updateInputs();
    gyroSystem.updateInputs();
  }

  public ChassisSpeeds createChassisSpeeds(
      double xSpeed, double ySpeed, double rotation, DriveMode driveMode) {
    return driveMode.equals(DriveMode.FIELD_CENTRIC)
        ? ChassisSpeeds.fromFieldRelativeSpeeds(
            xSpeed, ySpeed, rotation, Rotation2d.fromDegrees(getLastGyro()))
        : new ChassisSpeeds(xSpeed, ySpeed, rotation);
  }

  public void drive(ChassisSpeeds speeds) {
    SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.MAX_VELOCITY);
    setModuleStates(swerveModuleStates);
  }

  public ChassisSpeeds speedsFromStates() {
    return kinematics.toChassisSpeeds(
        frontLeft.getLatestState(),
        frontRight.getLatestState(),
        backLeft.getLatestState(),
        backRight.getLatestState());
  }

  private void setModuleStates(SwerveModuleState[] desiredStates) {
    Logger.recordOutput("desiredStates", desiredStates);
    frontLeft.setState(desiredStates[0]);
    frontRight.setState(desiredStates[1]);
    backLeft.setState(desiredStates[2]);
    backRight.setState(desiredStates[3]);
  }

  public void stopMotor() {
    frontLeft.stop();
    frontRight.stop();
    backLeft.stop();
    backRight.stop();
  }

  public void zeroRelativeEncoders() {
    frontLeft.resetRelativeEnc();
    frontRight.resetRelativeEnc();
    backLeft.resetRelativeEnc();
    backRight.resetRelativeEnc();
  }

  public void setSteerOffset(
      double absEncoderZeroFL,
      double absEncoderZeroFR,
      double absEncoderZeroBL,
      double absEncoderZeroBR) {
    frontLeft.setSteerOffset(absEncoderZeroFL);
    frontRight.setSteerOffset(absEncoderZeroFR);
    backLeft.setSteerOffset(absEncoderZeroBL);
    backRight.setSteerOffset(absEncoderZeroBR);
  }

  public void resetGyro() {
    gyroSystem.getIO().resetGyro();
  }

  public double getLastGyro() {
    return gyroSystem.getInputs().anglesInDeg;
  }

  public void setDriveMode(DriveMode driveMode) {
    this.driveMode = driveMode;
  }

  public DriveMode getDriveMode() {
    return driveMode;
  }

  public Pose2d getPose() {
    return poseEstimator.getEstimatedPose();
  }

  public void setGyroOffset(double offset) {
    gyroSystem.getIO().setAngleOffset(offset);
  }

  public void resetOdometry(Pose2d startingPosition) {
    poseEstimator.resetOdometry(
        startingPosition.getRotation().getRadians(), startingPosition.getTranslation());
  }

  public Rotation2d getGyroAngle() {
    return Rotation2d.fromDegrees(getLastGyro());
  }

  public ChassisSpeeds getChassisSpeeds() {
    return kinematics.toChassisSpeeds(
        frontLeft.getLatestState(),
        frontRight.getLatestState(),
        backLeft.getLatestState(),
        backRight.getLatestState());
  }

  public ChassisSpeeds getFieldChassisSpeeds() {
    return ChassisSpeeds.fromRobotRelativeSpeeds(getChassisSpeeds(), getPose().getRotation());
  }

  public void setFacingTarget(boolean facingTarget) {
    this.facingTarget = facingTarget;
  }

  public boolean isFacingTarget() {
    return facingTarget;
  }

  public void setVisionBaseSTD(Vector<N3> std) {
    ((DistanceVisionTruster) poseEstimator.getPoseManager().getVisionTruster()).setInitialSTD(std);
  }

  public Command generateTrajectoryCommand(Pose2d targetPose) {
    Trajectory trajectory =
        TrajectoryGenerator.generateTrajectory(
            getPose(), Collections.emptyList(), targetPose, trajectoryConfig);
    return new SwerveControllerCommand(
        trajectory,
        this::getPose,
        kinematics, // Position controllers
        finePathController,
        this::setModuleStates,
        this);
  }
}
