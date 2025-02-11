package frc.robot.utils.logging.subsystem.builders;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.processors.NeoPidMotorInputProcessor;

public class NeoPidMotorInputBuilder extends PidMotorInputBuilder<SparkMax> {

  public NeoPidMotorInputBuilder(String folder) {
    super(folder, new NeoPidMotorInputProcessor());
  }
}
