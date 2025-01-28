package frc.robot.subsystems.AlgaeByeByeTilt;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeTiltIO extends LoggableIO<AlgaeByeByeTiltInputs> {
  void setTiltSpeed(double speed);

  void stopTiltMotors();

  void resetTiltEncoder();
}
