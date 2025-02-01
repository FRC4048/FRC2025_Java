package frc.robot.subsystems.byebyeroller;

import frc.robot.utils.logging.LoggableIO;

public interface ByeByeRollerIO extends LoggableIO<ByeByeRollerInputs> {
  void setSpeed(double speed);

  void stopMotors();
}
