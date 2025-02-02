package frc.robot.subsystems.hihiroller;

import frc.robot.utils.logging.LoggableIO;

public interface HihiRollerIO extends LoggableIO<HihiRollerInputs> {
  void setRollerSpeed(double speed);

  void stopRollerMotor();
}
