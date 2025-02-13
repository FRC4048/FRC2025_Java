package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.PidMotorInputs;

public interface ElevatorIO extends LoggableIO<PidMotorInputs> {
  void setSpeed(double spd);

  public void setElevatorPosition(double encoderPos);

  public double getElevatorPosition();

  void stopMotor();

  void resetEncoder();
}
