package frc.robot.subsystems.AlgaeByeBye;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeIO extends LoggableIO<AlgaeByeByeInputs> {
  void setRemoverSpeed(double speed);

  void stopRemoverMotors();
}
