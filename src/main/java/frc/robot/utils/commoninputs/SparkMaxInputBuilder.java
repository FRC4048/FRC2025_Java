package frc.robot.utils.commoninputs;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;

public class SparkMaxInputBuilder extends MotorInputBuilder<SparkMax> {

  public SparkMaxInputBuilder(String key) {
    super(key);
  }

  @Override
  protected InputSource<Double, SparkMax> currentFromSource() {
    return SparkBase::getOutputCurrent;
  }

  @Override
  protected InputSource<Double, SparkMax> motorTemperatureFromSource() {
    return SparkBase::getMotorTemperature;
  }

  @Override
  protected InputSource<Double, SparkMax> encoderPositionFromSource() {
    return spark -> spark.getEncoder().getPosition();
  }

  @Override
  protected InputSource<Double, SparkMax> encoderVelocityFromSource() {
    return spark -> spark.getEncoder().getVelocity();
  }

  @Override
  protected InputSource<Boolean, SparkMax> fwdLimitFromSource() {
    return spark -> spark.getForwardLimitSwitch().isPressed();
  }

  @Override
  protected InputSource<Boolean, SparkMax> revLimitFromSource() {
    return spark -> spark.getReverseLimitSwitch().isPressed();
  }
}
