package frc.robot.subsystems.swervev3.io.steer;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;
import frc.robot.utils.motor.SparkUtil;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;

public class SimSteerMotorIO implements SwerveSteerMotorIO {
  private final SparkMax steerMotor;
  private final SparkMaxSim simMotor;
  private final DCMotor steerGearbox = DCMotor.getNEO(1);
  private final SparkBaseConfig steerConfig;
  private final SparkMaxInputProvider inputProvider;

  public SimSteerMotorIO(
      int steerMotorId, KinematicsConversionConfig conversionConfig, boolean steerInverted) {
    steerMotor = new SparkMax(steerMotorId, SparkMax.MotorType.kBrushless);
    simMotor = new SparkMaxSim(steerMotor, steerGearbox);
    steerConfig = new SparkMaxConfig();
    this.inputProvider = new SparkMaxInputProvider(steerMotor);
    setMotorConfig(steerInverted);
    setConversionFactors(conversionConfig);
    resetEncoder();
  }

  public void updateInputs(
      SteerMotorInputs inputs, SwerveModuleSimulation moduleSimulation, double steerAppliedVolts) {
    inputs.setSteerConnected(true);
    inputs.setEncoderPosition(moduleSimulation.getSteerAbsoluteFacing().getRadians());
    inputs.setEncoderVelocity(moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond));
    inputs.setAppliedOutput(steerAppliedVolts);
    inputs.setMotorCurrent(Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps)));
    inputs.setOdometryTimestamps(SparkUtil.getSimulationOdometryTimeStamps());
    inputs.setOdometryTurnPositions(moduleSimulation.getCachedSteerAbsolutePositions());
    inputs.process(inputProvider);
  }

  private void setMotorConfig(boolean steerInverted) {
    // driveMotor.restoreFactoryDefaults(); //TODO: idk what to do
    steerConfig.inverted(steerInverted).idleMode(SparkBaseConfig.IdleMode.kBrake);
    steerMotor.configure(
        steerConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    double steerPosConvFactor = 2 * Math.PI / conversionConfig.getProfile().getSteerGearRatio();
    steerConfig
        .encoder
        .positionConversionFactor(steerPosConvFactor)
        .velocityConversionFactor(steerPosConvFactor / 60);
    steerMotor.configure(
        steerConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setSteerVoltage(double volts) {
    steerMotor.setVoltage(volts);
  }

  @Override
  public void resetEncoder() {}

  @Override
  public void updateInputs(MotorInputs inputs) {}
}
