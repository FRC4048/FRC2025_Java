package frc.robot.subsystems.swervev3.io.drive;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.DriveMotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;
import frc.robot.utils.motor.SparkUtil;
import java.util.Arrays;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SimDriveMotorIO implements SimSwerveDriveMotorIO {
  private final SparkMax driveMotor;
  private final SparkMaxSim simMotor;
  private final DCMotor driveGearbox = DCMotor.getNEO(1);
  private final SparkBaseConfig driveConfig;
  private final SparkMaxInputProvider inputProvider;
  private final SwerveModuleSimulation moduleSimulation;
  private final PIDController drivePIDController;
  private double driveAppliedVolts = 0;
  private boolean driveClosedLoop = false;
  private double driveFFVolts;

  public SimDriveMotorIO(
      int driveMotorIO,
      KinematicsConversionConfig conversionConfig,
      boolean driveInverted,
      SwerveModuleSimulation moduleSimulation,
      PIDController drivePIDController,
      double driveFFVolts) {
    driveMotor = new SparkMax(driveMotorIO, SparkMax.MotorType.kBrushless);
    simMotor = new SparkMaxSim(driveMotor, driveGearbox);
    inputProvider = new SparkMaxInputProvider(driveMotor);
    driveConfig = new SparkMaxConfig();
    this.moduleSimulation = moduleSimulation;
    this.drivePIDController = drivePIDController;
    this.driveFFVolts = driveFFVolts;
    setMotorConfig(driveInverted);
    setConversionFactors(conversionConfig);
  }

  public void updateInputs(DriveMotorInputs inputs) {
    if (driveClosedLoop) {
      driveAppliedVolts =
          driveFFVolts
              + drivePIDController.calculate(
                  moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond));
    } else {
      drivePIDController.reset();
    }
    inputs.setDriveConnected(true);
    inputs.setEncoderPosition(moduleSimulation.getDriveWheelFinalPosition().in(Radians));
    inputs.setEncoderVelocity(moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond));
    inputs.setAppliedOutput(driveAppliedVolts);
    inputs.setMotorCurrent(Math.abs(moduleSimulation.getDriveMotorStatorCurrent().in(Amps)));
    inputs.setOdometryTimestamps(SparkUtil.getSimulationOdometryTimeStamps());
    inputs.setOdometryDrivePositionsRad(
        Arrays.stream(moduleSimulation.getCachedDriveWheelFinalPositions())
            .mapToDouble(angle -> angle.in(Radians))
            .toArray());
    inputs.process(inputProvider);
  }

  public SparkMax getDriveMotor() {
    return driveMotor;
  }

  private void setMotorConfig(boolean driveInverted) {
    // driveMotor.restoreFactoryDefaults(); //TODO: idk what to do
    driveConfig
        .inverted(driveInverted)
        .idleMode(SparkBaseConfig.IdleMode.kBrake)
        .closedLoopRampRate(Constants.DRIVE_RAMP_RATE_LIMIT)
        .secondaryCurrentLimit(Constants.DRIVE_SECONDARY_LIMIT)
        .smartCurrentLimit(
            Constants.DRIVE_SMART_LIMIT); // TODO: change current limiting because its different
    driveMotor.configure(
        driveConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    double driveVelConvFactor =
        (2 * conversionConfig.getWheelRadius() * Math.PI)
            / (conversionConfig.getProfile().getDriveGearRatio() * 60);
    double drivePosConvFactor =
        (2 * conversionConfig.getWheelRadius() * Math.PI)
            / (conversionConfig.getProfile().getDriveGearRatio());
    driveConfig
        .encoder
        .positionConversionFactor(drivePosConvFactor)
        .velocityConversionFactor(driveVelConvFactor);
    driveMotor.configure(
        driveConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void setDriveVoltage(double volts) {
    driveMotor.setVoltage(volts);
  }

  public void resetEncoder() {
    driveMotor.getEncoder().setPosition(0);
  }

  public void setDriveOpenLoop(double output) {
    driveClosedLoop = false;
    driveAppliedVolts = output;
  }

  public void setDriveVelocity(double velocityRadPerSec) {
    driveClosedLoop = true;
    driveFFVolts =
        Constants.DRIVE_PID_FF_S * Math.signum(velocityRadPerSec)
            + Constants.DRIVE_PID_FF_V * velocityRadPerSec;
    drivePIDController.setSetpoint(velocityRadPerSec);
  }
}
