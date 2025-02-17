package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;

public class MockElevatorIO implements ElevatorIO {
  @Override
  public void setSpeed(double spd) {}

  @Override
  public void updateInputs(PidMotorInputs inputs) {}

  @Override
  public void setElevatorPosition(double encoderPos) {}

  public double getElevatorPosition() {
    return 0;
  }

  public void stopMotor() {}

  @Override
  public void resetEncoder() {}

}
