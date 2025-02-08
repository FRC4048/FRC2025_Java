package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class MockElevatorIO implements ElevatorIO {
  @Override
  public void setSpeed(double spd) {}

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {}

  @Override
  public void stopMotor() {}

  @Override
  public void resetEncoder() {}
}
