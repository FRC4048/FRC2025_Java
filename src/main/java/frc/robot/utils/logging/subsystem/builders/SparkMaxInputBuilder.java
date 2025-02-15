package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.InputSource;

/** SparkMax Input Builder implementation */
public class SparkMaxInputBuilder extends MotorInputBuilder<SparkMax> {

  public SparkMaxInputBuilder(String folder) {
    super(folder);
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
    // return spark -> spark.getForwardLimitSwitch().isPressed();
    return spark -> {
      System.out.println("Is Pressed: " + spark.getForwardLimitSwitch().isPressed());
      return spark.getForwardLimitSwitch().isPressed();
    };
  }

  @Override
  protected InputSource<Boolean, SparkMax> revLimitFromSource() {
    return spark -> spark.getReverseLimitSwitch().isPressed();
  }
}
