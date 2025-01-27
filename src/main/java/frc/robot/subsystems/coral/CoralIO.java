package frc.robot.subsystems.coral;

import frc.robot.utils.logging.LoggableIO;

public interface CoralIO extends LoggableIO<CoralInputs> {
  void setShooterSpeed(double speed);

    void setTiltAngularVelocity(double angleVelocity);

  void stopShooterMotors();

  void stopTiltMotors();

  void resetTiltEncoder();
}
