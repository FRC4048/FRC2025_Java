package frc.robot.subsystems.coral;

import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class CoralInputs extends KeyedLoggableInputs {
  public double tiltEncoderPosition = 0;
  public boolean fwdTripped = false;
  public boolean revTripped = false;

  public CoralInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("encoderPosition", tiltEncoderPosition);
    table.put("fwdTripped", fwdTripped);
    table.put("revTripped", revTripped);
  }

  @Override
  public void fromLog(LogTable table) {
    tiltEncoderPosition = table.get("encoderPosition", tiltEncoderPosition);
    fwdTripped = table.get("fwdTripped", fwdTripped);
    revTripped = table.get("revTripped", revTripped);
  }
}
