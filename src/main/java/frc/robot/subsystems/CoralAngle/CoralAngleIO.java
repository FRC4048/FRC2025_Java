package frc.robot.subsystems.CoralAngle;

import frc.robot.utils.logging.LoggableIO;

public interface CoralAngleIO extends LoggableIO<CoralAngleInput> {

  void setTiltAngularVelocity(double angleVelocity);

  void stopTiltMotors();

  void resetTiltEncoder();
}
