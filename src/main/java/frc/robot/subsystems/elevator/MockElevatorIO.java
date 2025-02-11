package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class MockElevatorIO implements ElevatorIO {
  @Override
  public void setSpeed(double spd) {}

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {}

  @Override
  public void setElevatorPosition(double encoderPos) {}

  public double getElevatorPosition() {
    return 0;
  }

  public void stopMotor() {}

  @Override
  public void resetEncoder() {}
}
