package frc.robot.subsystems.elevator;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;

public interface ElevatorIO extends LoggableIO<PidMotorInputs> {
  void setSpeed(double spd);

  public void setElevatorPosition(double encoderPos);

  void stopMotor();

  void resetEncoder();

  void updatePidConfig(NeoPidConfig neoPidConfig);
}
