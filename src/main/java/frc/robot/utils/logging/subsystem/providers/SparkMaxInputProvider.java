package frc.robot.utils.logging.subsystem.providers;

import com.revrobotics.spark.SparkMax;

/** SparkMax Input Builder implementation */
public class SparkMaxInputProvider implements MotorInputProvider {

  private final SparkMax sparkMax;

  public SparkMaxInputProvider(SparkMax sparkMax) {
    this.sparkMax = sparkMax;
  }

  @Override
  public double getMotorCurrent() {
    return sparkMax.getOutputCurrent();
  }

  @Override
  public double getMotorTemperature() {
    return sparkMax.getMotorTemperature();
  }

  @Override
  public double getEncoderPosition() {
    return sparkMax.getEncoder().getPosition();
  }

  @Override
  public double getEncoderVelocity() {
    return sparkMax.getEncoder().getVelocity();
  }

  @Override
  public boolean getFwdLimit() {
    return sparkMax.getForwardLimitSwitch().isPressed();
  }

  @Override
  public boolean getRevLimit() {
    return sparkMax.getReverseLimitSwitch().isPressed();
  }

  @Override
  public double getAppliedOutput() {
    return sparkMax.getAppliedOutput();
  }
}
