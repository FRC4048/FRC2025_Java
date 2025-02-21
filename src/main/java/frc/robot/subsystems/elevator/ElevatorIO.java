package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.PIDLoggableIO;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;

public interface ElevatorIO extends PIDLoggableIO {
  void setSpeed(double spd);

  public void setElevatorPosition(double encoderPos);

  public double getElevatorPosition();

  void stopMotor();

  void resetEncoder();

  void updateInputs(PidMotorInputs inputs);
}
