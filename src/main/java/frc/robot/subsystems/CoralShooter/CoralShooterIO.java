package frc.robot.subsystems.CoralShooter;

import frc.robot.utils.logging.LoggableIO;

public interface CoralShooterIO extends LoggableIO<CoralShooterInput> {
  void setShooterSpeed(double speed);

  void stopShooterMotors();
}
