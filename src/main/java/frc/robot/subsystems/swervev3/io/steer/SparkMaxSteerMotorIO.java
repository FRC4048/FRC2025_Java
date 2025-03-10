package frc.robot.subsystems.swervev3.io.steer;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.subsystems.swervev3.KinematicsConversionConfig;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

public class SparkMaxSteerMotorIO implements SwerveSteerMotorIO {
  private final SparkMax steerMotor;
  private final SparkBaseConfig steerConfig;
  private final SparkMaxInputProvider inputProvider;

  public SparkMaxSteerMotorIO(
      int steerMotorId, KinematicsConversionConfig conversionConfig, boolean steerInverted) {
    steerMotor = new SparkMax(steerMotorId, SparkMax.MotorType.kBrushless);
    steerConfig = new SparkMaxConfig();
    this.inputProvider = new SparkMaxInputProvider(steerMotor);
    setMotorConfig(steerInverted);
    setConversionFactors(conversionConfig);
    resetEncoder();
  }

  private void setConversionFactors(KinematicsConversionConfig conversionConfig) {
    double steerPosConvFactor = 2 * Math.PI / conversionConfig.profile().getSteerGearRatio();
    steerConfig
        .encoder
        .positionConversionFactor(steerPosConvFactor)
        .velocityConversionFactor(steerPosConvFactor / 60);
    steerMotor.configure(
        steerConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  private void setMotorConfig(boolean steerInverted) {
    // driveMotor.restoreFactoryDefaults(); //TODO: idk what to do
    steerConfig.inverted(steerInverted).idleMode(IdleMode.kBrake);
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
  public void resetEncoder() {
    steerMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
