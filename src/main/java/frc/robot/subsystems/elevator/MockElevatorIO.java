package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;

public class MockElevatorIO implements ElevatorIO {
  @Override
  public void setSpeed(double spd) {}

  @Override
  public void updateInputs(PidMotorInputs inputs) {}

  @Override
  public void setElevatorPosition(double encoderPos) {}

  public void stopMotor() {}

  @Override
  public void resetEncoder() {}

  @Override
  public void setEncoder(double value) {}

  @Override
  public void configurePID(NeoPidConfig neoPidConfig) {}
}
