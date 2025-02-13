package frc.robot.utils.logging.subsystem.processors;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import java.util.function.Function;

/** SparkMax Input Builder implementation */
public class SparkMaxInputProcessor implements MotorInputProcessor<SparkMax> {

  @Override
  public Function<SparkMax, Double> currentFromSource() {
    return SparkBase::getOutputCurrent;
  }

  @Override
  public Function<SparkMax, Double> motorTemperatureFromSource() {
    return SparkBase::getMotorTemperature;
  }

  @Override
  public Function<SparkMax, Double> encoderPositionFromSource() {
    return spark -> spark.getEncoder().getPosition();
  }

  @Override
  public Function<SparkMax, Double> encoderVelocityFromSource() {
    return spark -> spark.getEncoder().getVelocity();
  }

  @Override
  public Function<SparkMax, Boolean> fwdLimitFromSource() {
    return spark -> spark.getForwardLimitSwitch().isPressed();
  }

  @Override
  public Function<SparkMax, Boolean> revLimitFromSource() {
    return spark -> spark.getReverseLimitSwitch().isPressed();
  }
}
