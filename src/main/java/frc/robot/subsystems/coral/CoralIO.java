package frc.robot.subsystems.coral;

import frc.robot.utils.LoggableIO;

public interface CoralIO extends LoggableIO<CoralInputs> {
  void setShooterSpeed(double speed);

  void setTiltAngularVelocity(double angleSpeed);

  void stopShooterMotors();

  void stopTiltMotors();

  void resetTiltEncoder();
}
