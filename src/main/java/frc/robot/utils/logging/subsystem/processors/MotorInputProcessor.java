package frc.robot.utils.logging.subsystem.processors;

public interface MotorInputProcessor<R> {
  InputSource<Double, R> currentFromSource();

  InputSource<Double, R> motorTemperatureFromSource();

  InputSource<Double, R> encoderPositionFromSource();

  InputSource<Double, R> encoderVelocityFromSource();

  InputSource<Boolean, R> fwdLimitFromSource();

  InputSource<Boolean, R> revLimitFromSource();
}
