package frc.robot.subsystems.elevator;

import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;

public class MockElevatorIO implements ElevatorIO {
  @Override
  public void setSpeed(double spd) {}

  @Override
  public void updateInputs(LimitedEncodedMotorInput inputs) {}
}
