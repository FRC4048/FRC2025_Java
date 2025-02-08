package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.InputSource;
import org.littletonrobotics.junction.LogTable;

/**
 * Double type of {@link Input}
 *
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public class DoubleInput<R> extends Input<Double, R> {
  public DoubleInput(String key, InputSource<Double, R> inputSource) {
    super(key, 0.0, inputSource);
  }

  public DoubleInput(String key, Double value, InputSource<Double, R> inputSource) {
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
