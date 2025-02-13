package frc.robot.utils.logging.subsystem.processors;

public interface MotorInputSource extends InputSource {

  double getMotorCurrent();

  double getMotorTemperature();

  double getEncoderPosition();

  double getEncoderVelocity();

  boolean getFwdLimit();

  boolean getRevLimit();
}
