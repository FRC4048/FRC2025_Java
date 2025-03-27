package frc.robot.subsystems.swervev3;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.Vector;
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
import edu.wpi.first.math.numbers.N3;
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
import frc.robot.subsystems.swervev3.io.SwerveModule;
import frc.robot.subsystems.swervev3.vision.DistanceVisionTruster;
import frc.robot.utils.Apriltag;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import java.util.Collections;
import java.util.function.Consumer;
import org.littletonrobotics.junction.Logger;

public class SwerveDrivetrain extends SubsystemBase {
  private final SwerveModule frontLeft;
  private final SwerveModule frontRight;
  private final SwerveModule backLeft;
  private final SwerveModule backRight;
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
  private boolean focusTagMade = false;
  private Apriltag focusedApriltag = Apriltag.ONE;
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
    finePathController.setTolerance(new Pose2d(0.02, 0.02, Rotation2d.fromDegrees(5)));
    trajectoryConfig = new TrajectoryConfig(0.5, 0.5);
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
    if (focusTagMade) {
      poseEstimator.updateVision(focusedApriltag);
    } else {
      poseEstimator.updateVision();
    }
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
    resetSimulationPoseCallBack.accept(startingPosition);
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

  public static SwerveDriveKinematics getKinematics() {
    return kinematics;
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

  public void setFocusedApriltag(Apriltag tagToFocus) {
    focusedApriltag = tagToFocus;
    focusTagMade = true;
  }

  public void exitFocusMode() {
    focusTagMade = false;
  }
}
