package frc.robot.subsystems.gyro;

import frc.robot.utils.logging.LoggableIO;

public interface GyroIO extends LoggableIO<GyroInputs> {
  void setAngleOffset(double offset);

  void resetGyro();
}
