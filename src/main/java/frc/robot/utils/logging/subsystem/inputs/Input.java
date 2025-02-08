package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.InputSource;
import org.littletonrobotics.junction.LogTable;

/**
 * Represents a value that can be logged (to and from a log table)
 * @param <T> Data type that is being logged
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public abstract class Input<T, R> {
  private final String key;
  private T value;
  private final InputSource<T, R> inputSource;

  public Input(String key, T value, InputSource<T, R> inputSource) {
    this.key = key;
    this.value = value;
    this.inputSource = inputSource;
  }

  public String getKey() {
    return key;
  }

  public T getValue() {
    return value;
  }

  protected void setValue(T value) {
    this.value = value;
  }

  public void process(R source) {
    value = inputSource.fromSource(source);
  }

  public abstract void toLog(LogTable table);

  public abstract void fromLog(LogTable table);
}
