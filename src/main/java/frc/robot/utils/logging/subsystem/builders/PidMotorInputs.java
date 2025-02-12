package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import frc.robot.utils.logging.subsystem.processors.PidMotorInputProcessor;
import org.littletonrobotics.junction.LogTable;

/** Contains Inputs that could be logged for a Motor with a Pid */
public class PidMotorInputs<R> extends MotorInputs<R> {
  private final DoubleInput<R> pidSetpoint;

  public PidMotorInputs(Builder<R, ?> builder) {
    super(builder);
    this.pidSetpoint = builder.pidSetpoint;
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

  public static class Builder<R, T extends Builder<R, T>> extends MotorInputs.Builder<R, T> {
    private DoubleInput<R> pidSetpoint;

    public Builder(String folder, PidMotorInputProcessor<R> inputProcessor) {
      super(folder, inputProcessor);
    }

    @Override
    public PidMotorInputs<R> build() {
      return new PidMotorInputs<>(this);
    }

    @Override
    public T addAll() {
      return super.addAll().pidSetpoint();
    }

    @Override
    public T reset() {
      super.reset();
      pidSetpoint = null;
      return self();
    }

    public T pidSetpoint() {
      pidSetpoint =
          new DoubleInput<>(
              "pidSetpoint", ((PidMotorInputProcessor<R>) inputProcessor).setpointFromSource());
      return self();
    }
  }
}
