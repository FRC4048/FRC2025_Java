package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;

public class MockElevatorIO implements ElevatorIO {
  @Override
  public void setSpeed(double spd) {}

  @Override
  public void updateInputs(BuildableKeyedInputs<SparkMax> inputs) {}
}
