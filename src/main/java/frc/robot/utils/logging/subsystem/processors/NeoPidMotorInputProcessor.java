package frc.robot.utils.logging.subsystem.processors;

import com.revrobotics.spark.SparkMax;

public class NeoPidMotorInputProcessor extends SparkMaxInputProcessor
    implements PidMotorInputProcessor<SparkMax> {

  @Override
  public InputSource<Double, SparkMax> setpointFromSource() {
    return null;
  }
}
