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
import frc.robot.subsystems.swervev3.io.drive.SwerveDriveMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SimSteerMotorIO;
import frc.robot.subsystems.swervev3.io.steer.SwerveSteerMotorIO;
import frc.robot.utils.ModulePosition;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.math.AngleUtils;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.drivesims.configs.SwerveModuleSimulationConfig;

public class SimSwerveModule implements ModuleIO {
  private final SwerveModuleSimulation moduleSimulation;
  private final SwerveModuleSimulationConfig simConfig;
  private final LoggableSystem<SwerveDriveMotorIO, MotorInputs> driveSystem;
  private final LoggableSystem<SwerveSteerMotorIO, MotorInputs> steerSystem;
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

  public SimSwerveModule(
      SwerveModuleSimulation moduleSimulation,
      SwerveDriveMotorIO driveMotorIO,
      SwerveSteerMotorIO steerMotorIO,
      SwerveAbsIO absIO,
      SwervePidConfig pidConfig,
      String moduleName,
      KinematicsConversionConfig kinematicsConfig) {
    MotorInputs driveInputs =
        new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().addStatus().build();
    MotorInputs steerInputs =
        new MotorInputBuilder<>("Drivetrain/" + moduleName).addEncoder().addStatus().build();
    this.driveSystem = new LoggableSystem<>(driveMotorIO, driveInputs);
    this.steerSystem = new LoggableSystem<>(steerMotorIO, steerInputs);
    this.absSystem = new LoggableSystem<>(absIO, new SwerveAbsInput("Drivetrain/" + moduleName));
    drivePIDController =
        new PIDController(
            pidConfig.getDrivePid().getP(),
            pidConfig.getDrivePid().getI(),
            pidConfig.getDrivePid().getD());
    turningPIDController =
        new ProfiledPIDController(
            pidConfig.getSteerPid().getP(),
            pidConfig.getSteerPid().getI(),
            pidConfig.getSteerPid().getD(),
            pidConfig.getGoalConstraint());
    driveFeedforward =
        new SimpleMotorFeedforward(
            pidConfig.getDriveGain().getS(), pidConfig.getDriveGain().getV());
    driveFFVolts = driveFeedforward.getKv();
    turnFeedforward =
        new SimpleMotorFeedforward(
            pidConfig.getSteerGain().getS(), pidConfig.getSteerGain().getV());
    turningPIDController.enableContinuousInput(0, Math.PI * 2);
    this.simConfig =
        new SwerveModuleSimulationConfig(
            driveMotor,
            steerMotor,
            kinematicsConfig.getProfile().getDriveGearRatio(),
            kinematicsConfig.getProfile().getSteerGearRatio(),
            Volts.of(Constants.DRIVE_PID_FF_S),
            Volts.of(Constants.STEER_PID_FF_S),
            Meters.of(kinematicsConfig.getWheelRadius()),
            KilogramSquareMeters.of(Constants.STEER_ROTATIONAL_INERTIA),
            Constants.COEFFICIENT_OF_FRICTION);
    this.moduleSimulation = new SwerveModuleSimulation(simConfig);
  }

  public void updateInputs() {
    // Run closed-loop control
    if (driveClosedLoop) {
      driveAppliedVolts =
          driveFFVolts
              + drivePIDController.calculate(
                  moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond));
    } else {
      drivePIDController.reset();
    }
    if (turnClosedLoop) {
      turnAppliedVolts =
          turningPIDController.calculate(moduleSimulation.getSteerAbsoluteFacing().getRadians());
    } else {
      turningPIDController.reset(absSystem.getInputs().absEncoderPosition); // TODO: might be wrong
    }

    // Update simulation state
    driveSystem.getIO().setDriveVoltage(driveAppliedVolts);
    steerSystem.getIO().setSteerVoltage(turnAppliedVolts);

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
    turnAppliedVolts = output;
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
    turningPIDController.setGoal(rotation.getRadians());
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
    driveSystem.getIO().setDriveVoltage(driveSpeed);
    steerSystem.getIO().setSteerVoltage(turnSpeed * 12);
  }

  public SwerveModuleState getLatestState() {
    return new SwerveModuleState(
        driveSystem.getInputs().getEncoderVelocity(), new Rotation2d(getSteerPosition()));
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

  public static SimSwerveModule createModule(
      SwerveModuleSimulation moduleSimulation,
      SwerveIdConfig idConf,
      KinematicsConversionConfig kinematicsConfig,
      SwervePidConfig pidConfig,
      ModulePosition position,
      boolean driveInverted) {
    SimDriveMotorIO frontLeftDriveMotorIO =
        new SimDriveMotorIO(idConf.getDriveMotorId(), kinematicsConfig, driveInverted);
    SimSteerMotorIO frontLeftSteerMotorIO =
        new SimSteerMotorIO(
            idConf.getTurnMotorId(),
            kinematicsConfig,
            kinematicsConfig.getProfile().isSteerInverted());
    CANCoderAbsIO frontLeftAbsIO = new CANCoderAbsIO(idConf.getCanCoderId());
    return new SimSwerveModule(
        moduleSimulation,
        frontLeftDriveMotorIO,
        frontLeftSteerMotorIO,
        frontLeftAbsIO,
        pidConfig,
        position.getLoggingKey(),
        kinematicsConfig);
  }

  public double getAbsPosition() {
    return absSystem.getInputs().absEncoderPosition;
  }

  private double getSteerPosition() {
    return AngleUtils.normalizeSwerveAngle(
        steerSystem.getInputs().getEncoderPosition() - steerOffset);
  }
}
