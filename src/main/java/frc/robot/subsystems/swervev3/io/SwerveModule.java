package frc.robot.subsystems.swervev3.io;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.SwerveIdConfig;
import frc.robot.subsystems.swervev3.SwervePidConfig;
import frc.robot.subsystems.swervev3.io.abs.CANCoderAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsInput;
import frc.robot.subsystems.swervev3.io.drive.SparkMaxDriveMotorIO;
import frc.robot.subsystems.swervev3.io.drive.SwerveDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SparkMaxSteerMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SwerveSteerMotorIO;
import frc.robot.utils.ModulePosition;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.math.AngleUtils;
import frc.robot.utils.motor.Gain;
import frc.robot.utils.motor.PID;

public class SwerveModule {
  private final LoggableSystem<SwerveDriveMotorIO, MotorInputs> driveSystem;
  private final LoggableSystem<SwerveSteerMotorIO, MotorInputs> steerSystem;
  private final LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem;
  private final PIDController drivePIDController;
  private final ProfiledPIDController turningPIDController;
  private final SimpleMotorFeedforward driveFeedforward;
  private final SimpleMotorFeedforward turnFeedforward;
  private double steerOffset;

  public SwerveModule(
      SwerveDriveMotorIO driveMotorIO,
      SwerveSteerMotorIO steerMotorIO,
      SwerveAbsIO absIO,
      PID drivePid,
      PID turnPid,
      Gain driveGain,
      Gain turnGain,
      TrapezoidProfile.Constraints goalConstraint,
      String moduleName) {
    MotorInputs driveInputs =
        new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().addStatus().build();
    MotorInputs steerInputs =
        new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().addStatus().build();
    this.driveSystem = new LoggableSystem<>(driveMotorIO, driveInputs);
    this.steerSystem = new LoggableSystem<>(steerMotorIO, steerInputs);
    this.absSystem = new LoggableSystem<>(absIO, new SwerveAbsInput("Drivetrain/" + moduleName));
    drivePIDController = new PIDController(drivePid.p(), drivePid.i(), drivePid.d());
    turningPIDController =
        new ProfiledPIDController(turnPid.p(), turnPid.i(), turnPid.d(), goalConstraint);
    driveFeedforward = new SimpleMotorFeedforward(driveGain.s(), driveGain.v());
    turnFeedforward = new SimpleMotorFeedforward(turnGain.s(), turnGain.v());
    turningPIDController.enableContinuousInput(0, Math.PI * 2);
  }

  public SwerveModule(
      SwerveDriveMotorIO driveMotorIO,
      SwerveSteerMotorIO steerMotorIO,
      SwerveAbsIO absIO,
      SwervePidConfig pidConfig,
      String moduleName) {
    this(
        driveMotorIO,
        steerMotorIO,
        absIO,
        pidConfig.drivePid(),
        pidConfig.steerPid(),
        pidConfig.driveGain(),
        pidConfig.steerGain(),
        pidConfig.goalConstraint(),
        moduleName);
  }

  public void setState(SwerveModuleState desiredState) {
    double steerEncoderPosition = getSteerPosition();
    desiredState.optimize(Rotation2d.fromRotations(steerEncoderPosition));
    double driveSpeed =
        drivePIDController.calculate(
                driveSystem.getInputs().getEncoderVelocity(), (desiredState.speedMetersPerSecond))
            + driveFeedforward.calculate(desiredState.speedMetersPerSecond);
    double turnSpeed =
        turningPIDController.calculate(steerEncoderPosition, desiredState.angle.getRadians())
            + turnFeedforward.calculate(turningPIDController.getSetpoint().velocity);
    driveSystem.getIO().setDriveVoltage(driveSpeed);
    steerSystem.getIO().setSteerVoltage(turnSpeed * 12);
  }

  public SwerveModuleState getLatestState() {
    return new SwerveModuleState(
        driveSystem.getInputs().getEncoderVelocity(), new Rotation2d(getSteerPosition()));
  }

  public void updateInputs() {
    absSystem.updateInputs();
    driveSystem.updateInputs();
    steerSystem.updateInputs();
  }

  public void stop() {
    driveSystem.getIO().setDriveVoltage(0);
    steerSystem.getIO().setSteerVoltage(0);
  }

  public void resetRelativeEnc() {
    driveSystem.getIO().resetEncoder();
    steerSystem.getIO().resetEncoder();
  }

  public void setSteerOffset(double zeroAbs) {
    steerSystem.getIO().resetEncoder();
    double offset = AngleUtils.normalizeAbsAngleRadians(zeroAbs - getAbsPosition());
    steerOffset = AngleUtils.normalizeSwerveAngle(offset);
  }

  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(
        driveSystem.getInputs().getEncoderPosition(), new Rotation2d(getSteerPosition()));
  }

  public static SwerveModule createModule(
      SwerveIdConfig idConf,
      KinematicsConversionConfig kinematicsConfig,
      SwervePidConfig pidConfig,
      ModulePosition position,
      boolean driveInverted) {
    SparkMaxDriveMotorIO frontLeftDriveMotorIO =
        new SparkMaxDriveMotorIO(idConf.driveMotorId(), kinematicsConfig, driveInverted);
    SparkMaxSteerMotorIO frontLeftSteerMotorIO =
        new SparkMaxSteerMotorIO(
            idConf.turnMotorId(),
            kinematicsConfig,
            kinematicsConfig.profile().isSteerInverted());
    CANCoderAbsIO frontLeftAbsIO = new CANCoderAbsIO(idConf.canCoderId());
    return new SwerveModule(
        frontLeftDriveMotorIO,
        frontLeftSteerMotorIO,
        frontLeftAbsIO,
        pidConfig,
        position.getLoggingKey());
  }

  public double getAbsPosition() {
    return absSystem.getInputs().absEncoderPosition;
  }

  private double getSteerPosition() {
    return AngleUtils.normalizeSwerveAngle(
        steerSystem.getInputs().getEncoderPosition() - steerOffset);
  }
}
