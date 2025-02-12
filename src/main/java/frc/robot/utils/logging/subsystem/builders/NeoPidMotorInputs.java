package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import frc.robot.utils.logging.subsystem.processors.NeoPidMotorInputProcessor;
import org.littletonrobotics.junction.LogTable;

public class NeoPidMotorInputs extends MotorInputs<SparkMax> {
  private final DoubleInput<SparkMax> pidSetpoint;

  public NeoPidMotorInputs(Builder<?> builder) {
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
  public void process(SparkMax source) {
    super.process(source);
    if (pidSetpoint != null) {
      pidSetpoint.process(source);
    }
  }

  public DoubleInput<SparkMax> getPidSetpoint() {
    return pidSetpoint;
  }

  public static class Builder<T extends Builder<T>> extends MotorInputs.Builder<SparkMax, T> {
    private DoubleInput<SparkMax> pidSetpoint;

    public Builder(String folder, NeoPidMotorInputProcessor inputProcessor) {
      super(folder, inputProcessor);
    }

    @Override
    public NeoPidMotorInputs build() {
      return new NeoPidMotorInputs(this);
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
              "pidSetpoint", ((NeoPidMotorInputProcessor) inputProcessor).setpointFromSource());
      return self();
    }
  }
}
