package frc.robot.subsystems.swervev3.io.drive;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.DriveMotorInputs;

public interface SimSwerveDriveMotorIO extends LoggableIO<DriveMotorInputs> {
  void setDriveVoltage(double volts);

  void resetEncoder();
}
