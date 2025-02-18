package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;

public interface ElevatorIO extends LoggableIO<PidMotorInputs> {
  void setSpeed(double spd);

  void setElevatorPosition(double encoderPos);

  double getElevatorPosition();

  void stopMotor();

  void resetEncoder();
}
