package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import frc.robot.utils.logging.subsystem.processors.NeoPidMotorInputProcessor;
import frc.robot.utils.logging.subsystem.processors.PidMotorInputProcessor;
import org.littletonrobotics.junction.LogTable;

/** Contains Inputs that could be logged for a SparkMax with a NeoMotorPid */
public class NeoPidMotorInputs extends PidMotorInputs<SparkMax> {

  public NeoPidMotorInputs(Builder<?> builder) {
    super(builder);
  }
  public static class Builder<T extends Builder<T>> extends PidMotorInputs.Builder<SparkMax, T> {

    public Builder(String folder) {
      super(folder, new NeoPidMotorInputProcessor());
    }

    @Override
    public NeoPidMotorInputs build() {
      return new NeoPidMotorInputs(this);
    }
  }
}
