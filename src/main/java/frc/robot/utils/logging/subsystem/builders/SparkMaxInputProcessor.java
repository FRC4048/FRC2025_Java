package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import frc.robot.utils.logging.subsystem.processors.MotorInputProcessor;

/** SparkMax Input Builder implementation */
public class SparkMaxInputProcessor implements MotorInputProcessor<SparkMax> {

  @Override
  public InputSource<Double, SparkMax> currentFromSource() {
    return SparkBase::getOutputCurrent;
  }

  @Override
  public InputSource<Double, SparkMax> motorTemperatureFromSource() {
    return SparkBase::getMotorTemperature;
  }

  @Override
  public InputSource<Double, SparkMax> encoderPositionFromSource() {
    return spark -> spark.getEncoder().getPosition();
  }

  @Override
  public InputSource<Double, SparkMax> encoderVelocityFromSource() {
    return spark -> spark.getEncoder().getVelocity();
  }

  @Override
  public InputSource<Boolean, SparkMax> fwdLimitFromSource() {
    return spark -> spark.getForwardLimitSwitch().isPressed();
  }

  @Override
  public InputSource<Boolean, SparkMax> revLimitFromSource() {
    return spark -> spark.getReverseLimitSwitch().isPressed();
  }
}
