package frc.robot.utils.commoninputs;

import org.littletonrobotics.junction.LogTable;

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

  abstract void toLog(LogTable table);

  abstract void fromLog(LogTable table);
}
