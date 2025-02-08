package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.LoggableIO;
import org.littletonrobotics.junction.Logger;

/**
 * abstraction for simple subsystems that have a singular ${@link LoggableIO}
 *
 * @param <T> Loggable subsystem type
 * @param <R> Loggable input type that the subsystem accepts
 */
public class LoggableSystem<T extends LoggableIO<R>, R extends KeyedLoggableInputs> {
  private final T io;
  private final R inputs;
  private final String key;

  public LoggableSystem(T io, R inputs) {
    this.io = io;
    this.inputs = inputs;
    this.key = "LoggableInputs/" + inputs.getKey();
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
