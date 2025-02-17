package frc.robot.subsystems.hihiroller;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface HihiRollerIO extends LoggableIO<MotorInputs> {
  void setRollerSpeed(double speed);

  void stopRollerMotor();
}
