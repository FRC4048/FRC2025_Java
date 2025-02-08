package frc.robot.subsystems.swervev3.io.drive;

import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class SwerveDriveMotorInput extends KeyedLoggableInputs {

  public double driveEncoderPosition = 0;
  public double driveEncoderVelocity = 0;
  public double driveCurrentDraw = 0;

  public SwerveDriveMotorInput(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("driveEncoderPosition", driveEncoderPosition);
    table.put("driveEncoderVelocity", driveEncoderVelocity);
    table.put("driveCurrentDraw", driveCurrentDraw);
  }

  @Override
  public void fromLog(LogTable table) {
    driveEncoderPosition = table.get("driveEncoderPosition", driveEncoderPosition);
    driveEncoderVelocity = table.get("driveEncoderVelocity", driveEncoderVelocity);
    driveCurrentDraw = table.get("driveCurrentDraw", driveCurrentDraw);
  }
}
