package frc.robot.subsystems.hihiextender;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

public class RealHihiExtenderIO implements HihiExtenderIO {
  private final SparkMax extenderMotor;
  private final SparkMaxInputProvider inputProvider;

  public RealHihiExtenderIO() {
    this.extenderMotor =
        new SparkMax(Constants.ALGAE_EXTENDER_MOTOR_ID, SparkLowLevel.MotorType.kBrushed);
    this.inputProvider = new SparkMaxInputProvider(extenderMotor);
    configureMotor();
    resetExtenderEncoder();
  }

  private void configureMotor() {
    SparkMaxConfig extenderMotorConfig = new SparkMaxConfig();
    extenderMotorConfig.idleMode(IdleMode.kBrake);
    extenderMotor.configure(
        extenderMotorConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void stopHihiExtenderMotor() {
    this.extenderMotor.set(0);
  }

  @Override
  public void setHihiExtenderSpeed(double speed) {
    this.extenderMotor.set(speed);
  }

  @Override
  public void resetExtenderEncoder() {}

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
