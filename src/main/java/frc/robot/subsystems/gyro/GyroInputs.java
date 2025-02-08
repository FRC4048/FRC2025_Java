package frc.robot.subsystems.gyro;

import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class GyroInputs extends KeyedLoggableInputs {
  public double anglesInDeg = 0;
  public double angleOffset = 0;

  public GyroInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("anglesInDeg", anglesInDeg);
    table.put("angleOffset", angleOffset);
  }

  @Override
  public void fromLog(LogTable table) {
    anglesInDeg = table.get("anglesInDeg", anglesInDeg);
    angleOffset = table.get("angleOffset", angleOffset);
  }
}
