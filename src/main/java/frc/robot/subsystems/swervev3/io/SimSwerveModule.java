package frc.robot.subsystems.swervev3.io;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.subsystems.swervev3.SwerveIdConfig;
import frc.robot.subsystems.swervev3.SwervePidConfig;
import frc.robot.subsystems.swervev3.io.abs.CANCoderAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsIO;
import frc.robot.subsystems.swervev3.io.abs.SwerveAbsInput;
import frc.robot.subsystems.swervev3.io.drive.SimDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SimSteerMotorIO;
import frc.robot.utils.ModulePosition;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.DriveMotorInputBuilder;
import frc.robot.utils.logging.subsystem.builders.SteerMotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.DriveMotorInputs;
import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;
import frc.robot.utils.math.AngleUtils;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SimSwerveModule implements ModuleIO {
  private final LoggableSystem<SimDriveMotorIO, DriveMotorInputs> driveSystem;
  private final LoggableSystem<SimSteerMotorIO, SteerMotorInputs> steerSystem;
  private final LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem;
  private final PIDController drivePIDController;
  private final ProfiledPIDController turningPIDController;
  private final SimpleMotorFeedforward driveFeedforward;
  private final SimpleMotorFeedforward turnFeedforward;
  private boolean driveClosedLoop = false;
  private boolean turnClosedLoop = false;
  private double steerOffset;
  private double driveFFVolts = 0.0;
  private double driveAppliedVolts = 0.0;
  private double turnAppliedVolts = 0.0;
  private final DCMotor driveMotor = DCMotor.getNEO(1);
  private final DCMotor steerMotor = DCMotor.getNEO(1);
  private String moduleName;

  public SimSwerveModule(
      SwerveModuleSimulation moduleSimulation,
      SimDriveMotorIO driveMotorIO,
      SimSteerMotorIO steerMotorIO,
      SwervePidConfig pidConfig,
      String moduleName,
      PIDController drivePIDController,
      ProfiledPIDController turnPIDController,
      SimpleMotorFeedforward driveFeedforward,
      LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSystem) {
    this.moduleName = moduleName;
    DriveMotorInputs driveInputs =
        (DriveMotorInputs)
            new DriveMotorInputBuilder<>("Drivetrain/" + moduleName)
                .addEncoder()
                .addStatus()
                .build();
    SteerMotorInputs steerInputs =
        (SteerMotorInputs)
            new SteerMotorInputBuilder<>("Drivetrain/" + moduleName)
                .addEncoder()
                .addStatus()
                .build();
    this.driveFeedforward = driveFeedforward;
    this.driveFFVolts = driveFeedforward.getKv();
    this.driveSystem = new LoggableSystem<>(driveMotorIO, driveInputs);
    this.steerSystem = new LoggableSystem<>(steerMotorIO, steerInputs);
    this.absSystem = absSystem;
    this.drivePIDController = drivePIDController;
    this.turningPIDController = turnPIDController;
    turnFeedforward =
        new SimpleMotorFeedforward(
            pidConfig.getSteerGain().getS(), pidConfig.getSteerGain().getV());
    turningPIDController.enableContinuousInput(0, Math.PI * 2);
  }

  public void updateInputs() {
    // Run closed-loop control

    // Update simulation state
    // driveSystem.getIO().setDriveVoltage(driveAppliedVolts);

    // Update drive inputs
    driveSystem.updateInputs();

    // Update turn inputs
    steerSystem.updateInputs();

    // Update abs inputs
    absSystem.updateInputs();
  }

  public void setDriveOpenLoop(double output) {
    driveClosedLoop = false;
    driveAppliedVolts = output;
  }

  public void setTurnOpenLoop(double output) {
    turnClosedLoop = false;
    steerSystem.getIO().setSteerOpenLoop(output);
  }

  public void setDriveVelocity(double velocityRadPerSec) {
    driveClosedLoop = true;
    driveFFVolts =
        Constants.DRIVE_PID_FF_S * Math.signum(velocityRadPerSec)
            + Constants.DRIVE_PID_FF_V * velocityRadPerSec;
    drivePIDController.setSetpoint(velocityRadPerSec);
  }

  public void setTurnPosition(Rotation2d rotation) {
    turnClosedLoop = true;
    steerSystem.getIO().setTurnPosition(rotation);
  }

  public void setState(SwerveModuleState desiredState) {
    double steerEncoderPosition = getSteerPosition();

    SwerveModuleState state =
        SwerveModuleState.optimize(desiredState, new Rotation2d(steerEncoderPosition));
    double driveSpeed =
        drivePIDController.calculate(
                driveSystem.getInputs().getEncoderVelocity(), (state.speedMetersPerSecond))
            + driveFeedforward.calculate(state.speedMetersPerSecond);
    double turnSpeed =
        turningPIDController.calculate(steerEncoderPosition, state.angle.getRadians())
            + turnFeedforward.calculate(turningPIDController.getSetpoint().velocity);
    driveSystem.getIO().setDriveVelocity(driveSpeed);
    steerSystem.getIO().setSteerVelocity(turnSpeed);
  }

  public SwerveModuleState getLatestState() {
    return new SwerveModuleState(
        driveSystem.getInputs().getEncoderVelocity(), new Rotation2d(getSteerPosition()));
  }

  public void stop() {
    driveSystem.getIO().setDriveVelocity(0);
    steerSystem.getIO().setSteerVelocity(0);
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

  public static SimSwerveModule createModule(
      SwerveModuleSimulation moduleSimulation,
      SwerveIdConfig idConf,
      KinematicsConversionConfig kinematicsConfig,
      SwervePidConfig pidConfig,
      ModulePosition position,
      boolean driveInverted) {
    PIDController driveController =
        new PIDController(
            pidConfig.getDrivePid().getP(),
            pidConfig.getDrivePid().getI(),
            pidConfig.getDrivePid().getD());
    ProfiledPIDController turningController =
        new ProfiledPIDController(
            pidConfig.getSteerPid().getP(),
            pidConfig.getSteerPid().getI(),
            pidConfig.getSteerPid().getD(),
            pidConfig.getGoalConstraint());
    SimpleMotorFeedforward driveFF =
        new SimpleMotorFeedforward(
            pidConfig.getDriveGain().getS(), pidConfig.getDriveGain().getV());
    CANCoderAbsIO frontLeftAbsIO = new CANCoderAbsIO(idConf.getCanCoderId());
    LoggableSystem<SwerveAbsIO, SwerveAbsInput> absSys =
        new LoggableSystem<>(
            frontLeftAbsIO, new SwerveAbsInput("Drivetrain/" + position.getLoggingKey()));
    SimDriveMotorIO frontLeftDriveMotorIO =
        new SimDriveMotorIO(
            idConf.getDriveMotorId(),
            kinematicsConfig,
            driveInverted,
            moduleSimulation,
            driveController,
            position.getLoggingKey());
    SimSteerMotorIO frontLeftSteerMotorIO =
        new SimSteerMotorIO(
            idConf.getTurnMotorId(),
            kinematicsConfig,
            kinematicsConfig.getProfile().isSteerInverted(),
            moduleSimulation,
            turningController,
            absSys,
            position.getLoggingKey());

    return new SimSwerveModule(
        moduleSimulation,
        frontLeftDriveMotorIO,
        frontLeftSteerMotorIO,
        pidConfig,
        position.getLoggingKey(),
        driveController,
        turningController,
        driveFF,
        absSys);
  }

  public double getAbsPosition() {
    return absSystem.getInputs().absEncoderPosition;
  }

  private double getSteerPosition() {
    return AngleUtils.normalizeSwerveAngle(
        steerSystem.getInputs().getEncoderPosition() - steerOffset);
  }
}
