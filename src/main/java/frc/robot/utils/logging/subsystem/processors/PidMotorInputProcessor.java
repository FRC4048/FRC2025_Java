package frc.robot.utils.logging.subsystem.processors;

import java.util.function.Function;

public interface PidMotorInputProcessor<R> extends MotorInputProcessor<R> {
  Function<R, Double> setpointFromSource();
}
