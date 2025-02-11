package frc.robot.subsystems.coral;

import frc.robot.utils.logging.LoggableIO;

public interface CoralIO extends LoggableIO<CoralInputs> {
  void setShooterSpeed(double speed);

  void stopShooterMotors();

  void enableOrDisableLimitSwitch(boolean state);
}
