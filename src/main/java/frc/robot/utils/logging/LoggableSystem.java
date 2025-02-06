package frc.robot.utils.logging;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/**
 * abstraction for simple subsystems that have a singular ${@link LoggableIO}
 *
 * @param <T> Loggable subsystem type
 * @param <R> Loggable input type that the subsystem accepts
 */
public class LoggableSystem<T extends LoggableIO<R>, R extends LoggableInputs> {
  private final T io;
  private final R inputs;
  private final String key;

  public LoggableSystem(T io, R inputs) {
    this.io = io;
    this.inputs = inputs;
    this.key = "LoggableSystemInputs";
  }

  public LoggableSystem(T io, R inputs, Class<?>... subsystemClass) {
    this.io = io;
    this.inputs = inputs;
    StringBuilder builder = new StringBuilder("LoggableSystemInputs");
    for (Class<?> clz : subsystemClass) {
      builder.append("/").append(clz.getSimpleName());
    }
    this.key = builder.toString();
  }

  public LoggableSystem(T io, R inputs, String key, Class<?>... subsystemClass) {
    this.io = io;
    this.inputs = inputs;
    StringBuilder builder = new StringBuilder("LoggableSystemInputs");
    for (Class<?> clz : subsystemClass) {
      builder.append("/").append(clz.getSimpleName());
    }
    this.key = builder.append("/").append(key).toString();
  }

  public LoggableSystem(T io, R inputs, String key) {
    this.io = io;
    this.inputs = inputs;
    this.key = "LoggableSystemInputs/" + key;
  }

  /**
   * When called while robot is running in the real world, the method logs the data stored in inputs
   * When running in simulation, data from the log is injected into inputs
   */
  public void updateInputs() {
    io.updateInputs(inputs);
    Logger.processInputs(key, inputs);
  }

  public T getIO() {
    return io;
  }

  public R getInputs() {
    return inputs;
  }

  public String getKey() {
    return key;
  }
}
