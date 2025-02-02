package frc.robot.subsystems.algaebyebyetilt;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeTiltIO extends LoggableIO<AlgaeByeByeTiltInputs> {
  void setSpeed(double speed);

  void stopMotors();

  void resetEncoder();
}
