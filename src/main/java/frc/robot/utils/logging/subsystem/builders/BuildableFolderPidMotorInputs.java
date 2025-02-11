package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.BooleanInput;
import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import org.littletonrobotics.junction.LogTable;

/**
 * Contains Inputs that could be logged for a motor
 *
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public class BuildableFolderPidMotorInputs<R> extends BuildableFolderMotorInputs<R> {
  private final DoubleInput<R> pidSetpoint;

  public BuildableFolderPidMotorInputs(
      String key,
      DoubleInput<R> encoderPosition,
      DoubleInput<R> encoderVelocity,
      DoubleInput<R> motorCurrent,
      DoubleInput<R> motorTemperature,
      BooleanInput<R> fwdLimit,
      BooleanInput<R> revLimit,
      DoubleInput<R> pidSetpoint) {
    super(
        key, encoderPosition, encoderVelocity, motorCurrent, motorTemperature, fwdLimit, revLimit);
    this.pidSetpoint = pidSetpoint;
  }

  @Override
  public void toLog(LogTable table) {
    super.toLog(table);
    if (pidSetpoint != null) {
      pidSetpoint.toLog(table);
    }
  }

  @Override
  public void fromLog(LogTable table) {
    super.fromLog(table);
    if (pidSetpoint != null) {
      pidSetpoint.fromLog(table);
    }
  }

  @Override
  public void process(R source) {
    super.process(source);
    if (pidSetpoint != null) {
      pidSetpoint.process(source);
    }
  }

  public DoubleInput<R> getPidSetpoint() {
    return pidSetpoint;
  }
}
