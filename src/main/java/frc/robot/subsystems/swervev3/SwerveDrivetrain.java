package frc.robot.subsystems.swervev3;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.apriltags.ApriltagInputs;
import frc.robot.constants.Constants;
import frc.robot.subsystems.gyro.GyroIO;
import frc.robot.subsystems.gyro.GyroInputs;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.estimation.PoseEstimator;
import frc.robot.subsystems.swervev3.io.ModuleIO;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.shuffleboard.SmartShuffleboard;
import java.util.Collections;
import java.util.function.Consumer;
import org.ironmaple.simulation.drivesims.COTS;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;
import org.ironmaple.simulation.drivesims.configs.SwerveModuleSimulationConfig;
import org.littletonrobotics.junction.Logger;

public class SwerveDrivetrain extends SubsystemBase {
  private final ModuleIO frontLeft;
  private final ModuleIO frontRight;
  private final ModuleIO backLeft;
  private final ModuleIO backRight;
  private static final Translation2d frontLeftLocation =
      new Translation2d(Constants.DRIVE_BASE_WIDTH / 2, Constants.DRIVE_BASE_LENGTH / 2);
  private static final Translation2d frontRightLocation =
      new Translation2d(Constants.DRIVE_BASE_WIDTH / 2, -Constants.DRIVE_BASE_LENGTH / 2);
  private static final Translation2d backLeftLocation =
      new Translation2d(-Constants.DRIVE_BASE_WIDTH / 2, Constants.DRIVE_BASE_LENGTH / 2);
  private static final Translation2d backRightLocation =
      new Translation2d(-Constants.DRIVE_BASE_WIDTH / 2, -Constants.DRIVE_BASE_LENGTH / 2);
  private static final SwerveDriveKinematics kinematics =
      new SwerveDriveKinematics(
          frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);
  private final LoggableSystem<GyroIO, GyroInputs> gyroSystem;
  private DriveMode driveMode = DriveMode.FIELD_CENTRIC;
  private final PoseEstimator poseEstimator;
  private boolean facingTarget = false;
  private final Consumer<Pose2d> resetSimulationPoseCallBack;
  private TrajectoryConfig trajectoryConfig;
  // controller will add an additional meter per second in the x direction for every meter of error
  // in the x direction
  private final HolonomicDriveController finePathController =
      new HolonomicDriveController(
          new PIDController(1, 0, 0),
          new PIDController(1, 0, 0),
          new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14)));

  public SwerveDrivetrain(
      ModuleIO frontLeftModule,
      ModuleIO frontRightModule,
      ModuleIO backLeftModule,
      ModuleIO backRightModule,
      GyroIO gyroIO,
      LoggableIO<ApriltagInputs> apriltagIO,
      Consumer<Pose2d> resetSimulationPoseCallBack) {
    this.frontLeft = frontLeftModule;
    this.frontRight = frontRightModule;
    this.backLeft = backLeftModule;
    this.backRight = backRightModule;
    this.gyroSystem = new LoggableSystem<>(gyroIO, new GyroInputs("Gyro"));
    this.poseEstimator =
        new PoseEstimator(
            frontLeft, frontRight, backLeft, backRight, apriltagIO, kinematics, getLastGyro());
    this.resetSimulationPoseCallBack = resetSimulationPoseCallBack;
    finePathController.setTolerance(new Pose2d(0.025, 0.025, Rotation2d.fromDegrees(5)));
    trajectoryConfig = new TrajectoryConfig(0.25, 0.25);
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
    if (Constants.SWERVE_DEBUG) {
      SmartShuffleboard.put("Drive", "FL ABS Pos", frontLeft.getAbsPosition());
      SmartShuffleboard.put("Drive", "FR ABS Pos", frontRight.getAbsPosition());
      SmartShuffleboard.put("Drive", "BL ABS Pos", backLeft.getAbsPosition());
      SmartShuffleboard.put("Drive", "BR ABS Pos", backRight.getAbsPosition());
    }
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
    resetSimulationPoseCallBack.accept(startingPosition);
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

  private static final DCMotor driveMotor = DCMotor.getNEO(1);
  private static final DCMotor steerMotor = DCMotor.getNEO(1);
  private static final KinematicsConversionConfig kinematicsConfig =
      new KinematicsConversionConfig(Constants.WHEEL_RADIUS, Constants.SWERVE_MODULE_PROFILE);
  public static final DriveTrainSimulationConfig mapleSimConfig =
      DriveTrainSimulationConfig.Default()
          .withCustomModuleTranslations(kinematics.getModules())
          .withRobotMass(Kilograms.of(Constants.ROBOT_MASS))
          .withBumperSize(
              Meters.of(Constants.ROBOT_BUMPER_LENGTH), Meters.of(Constants.ROBOT_BUMPER_WIDTH))
          .withGyro(COTS.ofNav2X())
          .withSwerveModule(
              new SwerveModuleSimulationConfig(
                  driveMotor,
                  steerMotor,
                  kinematicsConfig.getProfile().getDriveGearRatio(),
                  kinematicsConfig.getProfile().getSteerGearRatio(),
                  Volts.of(Constants.DRIVE_PID_FF_S),
                  Volts.of(Constants.STEER_PID_FF_S),
                  Meters.of(kinematicsConfig.getWheelRadius()),
                  KilogramSquareMeters.of(Constants.STEER_ROTATIONAL_INERTIA),
                  Constants.COEFFICIENT_OF_FRICTION));

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
