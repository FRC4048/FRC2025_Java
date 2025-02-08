package frc.robot.utils.commoninputs;

import org.littletonrobotics.junction.LogTable;

public class DoubleInput<R> extends Input<Double, R> {
  public DoubleInput(String key, InputSource<Double, R> inputSource) {
    super(key, 0.0, inputSource);
  }

  public DoubleInput(String key, Double value, InputSource<Double, R> inputSource) {
    super(key, value, inputSource);
  }

  @Override
  void toLog(LogTable table) {
    table.put(getKey(), getValue());
  }

  @Override
  void fromLog(LogTable table) {
    setValue(table.get(getKey(), getValue()));
  }
}
