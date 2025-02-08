package frc.robot.utils.commoninputs;

import org.littletonrobotics.junction.LogTable;

public class BooleanInput<R> extends Input<Boolean, R> {

  public BooleanInput(String key, InputSource<Boolean, R> inputSource) {
    super(key, false, inputSource);
  }

  public BooleanInput(String key, Boolean value, InputSource<Boolean, R> inputSource) {
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
