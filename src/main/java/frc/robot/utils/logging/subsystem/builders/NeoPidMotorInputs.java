package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.processors.NeoPidMotorInputProcessor;
import frc.robot.utils.motor.NeoPidMotor;

/** Contains Inputs that could be logged for a SparkMax with a NeoMotorPid */
public class NeoPidMotorInputs extends PidMotorInputs<NeoPidMotor> {

  public NeoPidMotorInputs(Builder<?> builder) {
    super(builder);
  }

  public static class Builder<T extends Builder<T>> extends PidMotorInputs.Builder<NeoPidMotor, T> {

    public Builder(String folder) {
      super(folder, new NeoPidMotorInputProcessor());
    }

    @Override
    public NeoPidMotorInputs build() {
      return new NeoPidMotorInputs(this);
    }
  }
}
