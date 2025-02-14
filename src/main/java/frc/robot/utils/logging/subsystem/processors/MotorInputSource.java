package frc.robot.utils.logging.subsystem.processors;

import java.util.function.Function;

/**
 * Declares data that can be queried from a motor
 *
 * @param <R> Hardware class that is used to query data from hardware.
 */
public interface MotorInputSource<R> extends InputSource<R> {
  Function<R, Double> currentFromSource();

  Function<R, Double> motorTemperatureFromSource();

  Function<R, Double> encoderPositionFromSource();

  Function<R, Double> encoderVelocityFromSource();

  Function<R, Boolean> fwdLimitFromSource();

  Function<R, Boolean> revLimitFromSource();
}
