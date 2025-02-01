package frc.robot.subsystems.hihiRoller;

import frc.robot.utils.logging.LoggableIO;

public interface HiHiRollerIO extends LoggableIO<HiHiRollerInputs> {
  void setRollerSpeed(double speed);

  void stopRollerMotor();
}
