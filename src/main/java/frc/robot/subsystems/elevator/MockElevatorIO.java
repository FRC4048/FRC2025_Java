package frc.robot.subsystems.elevator;

public class MockElevatorIO implements ElevatorIO {
  @Override
  public void setSpeed(double spd) {}

  @Override
  public void updateInputs(ElevatorInputs inputs) {}

  @Override
  public void stopMotor() {}

  @Override
  public void resetEncoder() {}
}
