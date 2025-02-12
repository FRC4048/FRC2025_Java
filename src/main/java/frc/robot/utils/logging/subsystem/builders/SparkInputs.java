package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.processors.SparkMaxInputProcessor;

/**
 * This does not add any new functionality compared to MotorInputs with a SparkInputProcessor. This
 * is solely to make user API more friendly (generic can be scary)
 */
public class SparkInputs extends MotorInputs<SparkMax> {

  public SparkInputs(Builder<?> builder) {
    super(builder);
  }

  public static class Builder<T extends NeoPidMotorInputs.Builder<T>>
      extends MotorInputs.Builder<SparkMax, T> {

    public Builder(String folder) {
      super(folder, new SparkMaxInputProcessor());
    }
  }
}
