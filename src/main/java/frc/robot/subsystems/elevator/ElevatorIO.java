package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.PIDLoggableIO;
import frc.robot.utils.motor.NeoPidConfig;

public interface ElevatorIO extends PIDLoggableIO {
  void setSpeed(double spd);

  public void setElevatorPosition(double encoderPos);

  void stopMotor();

  void resetEncoder();

  void setEncoder(double value);

  void configurePID(NeoPidConfig neoPidConfig);
}
