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
import frc.robot.subsystems.swervev3.io.drive.SimDriveMotorIO;
import frc.robot.subsystems.swervev3.io.drive.SparkMaxDriveMotorIO;
import frc.robot.subsystems.swervev3.io.drive.SwerveDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SimSteerMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SparkMaxSteerMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SwerveSteerMotorIO;
import frc.robot.utils.ModulePosition;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.math.AngleUtils;
import frc.robot.utils.motor.Gain;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

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
      PIDController driveController,
      ProfiledPIDController turnController,
      Gain driveGain,
      Gain turnGain,
      TrapezoidProfile.Constraints goalConstraint,
      String moduleName) {
    MotorInputs driveInputs =
        new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().motorCurrent().build();
    MotorInputs steerInputs =
        new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().motorCurrent().build();
    this.driveSystem = new LoggableSystem<>(driveMotorIO, driveInputs);
    this.steerSystem = new LoggableSystem<>(steerMotorIO, steerInputs);
    this.absSystem = new LoggableSystem<>(absIO, new SwerveAbsInput("Drivetrain/" + moduleName));
    drivePIDController = driveController;
    turningPIDController = turnController;
    driveFeedforward = new SimpleMotorFeedforward(driveGain.s(), driveGain.v());
    turnFeedforward = new SimpleMotorFeedforward(turnGain.s(), turnGain.v());
    turningPIDController.enableContinuousInput(0, Math.PI * 2);
  }

  public SwerveModule(
      SwerveDriveMotorIO driveMotorIO,
      SwerveSteerMotorIO steerMotorIO,
      SwerveAbsIO absIO,
      PIDController driveController,
      ProfiledPIDController turnController,
      SwervePidConfig pidConfig,
      String moduleName) {
    this(
        driveMotorIO,
        steerMotorIO,
        absIO,
        driveController,
        turnController,
        pidConfig.driveGain(),
        pidConfig.steerGain(),
        pidConfig.goalConstraint(),
        moduleName);
  }

  public void setState(SwerveModuleState desiredState) {
    double steerEncoderPosition = getSteerPosition();
    SwerveModuleState state =
        SwerveModuleState.optimize(desiredState, new Rotation2d(steerEncoderPosition));
    double driveSpeed =
        drivePIDController.calculate(
                driveSystem.getInputs().getEncoderVelocity(), (state.speedMetersPerSecond))
            + driveFeedforward.calculate(state.speedMetersPerSecond);
    driveSystem.getIO().setDriveVoltage(driveSpeed);
    steerSystem
        .getIO()
        .setSteerVoltage(
            turningPIDController.calculate(steerEncoderPosition, state.angle.getRadians())
                + turnFeedforward.calculate(turningPIDController.getSetpoint().velocity) * 12);
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
      SwerveModuleSimulation moduleSimulation,
      SwerveIdConfig idConf,
      KinematicsConversionConfig kinematicsConfig,
      SwervePidConfig pidConfig,
      ModulePosition position,
      boolean driveInverted,
      boolean simulate) {
    PIDController driveController =
        new PIDController(
            pidConfig.drivePid().p(), pidConfig.drivePid().i(), pidConfig.drivePid().d());
    ProfiledPIDController turningController =
        new ProfiledPIDController(
            pidConfig.steerPid().p(),
            pidConfig.steerPid().i(),
            pidConfig.steerPid().d(),
            pidConfig.goalConstraint());
    SwerveDriveMotorIO frontLeftDriveMotorIO;
    SwerveSteerMotorIO frontLeftSteerMotorIO;
    if (simulate) {
      frontLeftDriveMotorIO =
          new SimDriveMotorIO(kinematicsConfig, moduleSimulation, driveController);
      frontLeftSteerMotorIO = new SimSteerMotorIO(moduleSimulation, turningController);
    } else {
      frontLeftDriveMotorIO =
          new SparkMaxDriveMotorIO(idConf.driveMotorId(), kinematicsConfig, driveInverted);
      frontLeftSteerMotorIO =
          new SparkMaxSteerMotorIO(
              idConf.turnMotorId(), kinematicsConfig, kinematicsConfig.profile().isSteerInverted());
    }
    CANCoderAbsIO frontLeftAbsIO = new CANCoderAbsIO(idConf.canCoderId());

    return new SwerveModule(
        frontLeftDriveMotorIO,
        frontLeftSteerMotorIO,
        frontLeftAbsIO,
        driveController,
        turningController,
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
