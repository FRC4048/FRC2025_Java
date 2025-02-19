package frc.robot.utils.logging.subsystem.providers;

public interface MotorInputProvider extends InputProvider {

  double getMotorCurrent();

  double getMotorTemperature();

  double getEncoderPosition();

  double getAlternateEncoderPosition();

  double getEncoderVelocity();

  boolean getFwdLimit();

  boolean getRevLimit();
}
