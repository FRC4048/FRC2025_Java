package frc.robot.subsystems.swervev3.io.drive;

import frc.robot.utils.logging.LoggableIO;

public interface SwerveDriveMotorIO extends LoggableIO<SwerveDriveMotorInput> {
  void setDriveVoltage(double volts);

  void resetEncoder();
}
