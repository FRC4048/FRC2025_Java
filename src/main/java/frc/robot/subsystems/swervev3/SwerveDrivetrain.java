package frc.robot.subsystems.swervev3;

import static edu.wpi.first.units.Units.*;

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
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.apriltags.ApriltagInputs;
import frc.robot.constants.Constants;
import frc.robot.subsystems.gyro.GyroIO;
import frc.robot.subsystems.gyro.GyroInputs;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.estimation.PoseEstimator;
import frc.robot.subsystems.swervev3.io.ModuleIO;
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.subsystems.swervev3.vision.DistanceVisionTruster;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.LoggableSystem;
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

  public void setVisionBaseSTD(Vector<N3> std) {
    ((DistanceVisionTruster) poseEstimator.getPoseManager().getVisionTruster()).setInitialSTD(std);
  }
}
