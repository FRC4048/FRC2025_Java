package frc.robot.utils.logging.subsystem.processors;

import java.util.function.Function;

public interface MotorInputProcessor<R> extends InputProcessor<R> {
  Function<R, Double> currentFromSource();

  Function<R, Double> motorTemperatureFromSource();

  Function<R, Double> encoderPositionFromSource();

  Function<R, Double> encoderVelocityFromSource();

  Function<R, Boolean> fwdLimitFromSource();

  Function<R, Boolean> revLimitFromSource();
}
