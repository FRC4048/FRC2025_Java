package frc.robot.subsystems.algaeRoller;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeRollerIO extends LoggableIO<AlgaeRollerInputs> {
  void setSpeed(double speed);

  void stop();
}
