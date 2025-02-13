package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.InputSource;
import org.littletonrobotics.junction.LogTable;

/**
 * Boolean type of {@link Input}
 *
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public class BooleanInput<R> extends Input<Boolean, R> {

  public BooleanInput(String key, InputSource<Boolean, R> inputSource) {
    super(key, false, inputSource);
  }

  public BooleanInput(String key, Boolean value, InputSource<Boolean, R> inputSource) {
    super(key, value, inputSource);
  }

  @Override
  public void toLog(LogTable table) {
    table.put(getKey(), getValue());
  }

  @Override
  public void fromLog(LogTable table) {
    setValue(table.get(getKey(), getValue()));
  }
}
