package frc.robot.subsystems.coral;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class CoralInputs implements LoggableInputs {
  public double tiltEncoderPosition = 0;
  public boolean fwdTripped = false;
  public boolean revTripped = false;

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
