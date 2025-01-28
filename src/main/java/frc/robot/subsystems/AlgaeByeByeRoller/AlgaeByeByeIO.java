package frc.robot.subsystems.AlgaeByeByeRoller;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeIO extends LoggableIO<AlgaeByeByeInputs> {
  void setRemoverSpeed(double speed);

  void stopRemoverMotors();
}
