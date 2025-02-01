package frc.robot.subsystems.ByeByeTilt;

import frc.robot.utils.logging.LoggableIO;

public interface ByeByeTiltIO extends LoggableIO<ByeByeTiltInputs> {
  void setSpeed(double speed);

  void stopMotors();

  void resetEncoder();
}
