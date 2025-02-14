package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.processors.SparkMaxInputSource;

/**
 * This does not add any new functionality compared to {@link MotorInputs} with a {@link
 * SparkMaxInputSource}. <br>
 * This is solely to make user API more friendly.
 */
public class SparkInputs extends MotorInputs<SparkMax> {

  public SparkInputs(Builder<?> builder) {
    super(builder);
  }

  public static class Builder<T extends MotorInputs.Builder<SparkMax, T>>
      extends MotorInputs.Builder<SparkMax, T> {

    public Builder(String folder) {
      super(folder, new SparkMaxInputSource());
    }

    @Override
    public SparkInputs build() {
      return new SparkInputs(this);
    }
  }
}
