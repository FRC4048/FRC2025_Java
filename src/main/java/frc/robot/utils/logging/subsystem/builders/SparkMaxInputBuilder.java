package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.processors.SparkMaxInputProcessor;

public class SparkMaxInputBuilder extends MotorInputBuilder<SparkMax> {
  public SparkMaxInputBuilder(String folder) {
    super(folder, new SparkMaxInputProcessor());
  }
}
