package frc.robot.utils.logging.subsystem.processors;

import java.util.function.Function;

public interface PidMotorInputSource<R> extends MotorInputSource<R> {
  Function<R, Double> setpointFromSource();
}
