package frc.robot.subsystems.gyro;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class GyroInputs extends FolderLoggableInputs {
  public double anglesInDeg = 0;
  public double angleOffset = 0;
  public double accelX;
  public double accelY;
  public double accelZ;
  public double velX;
  public double velY;
  public double velZ;

  public GyroInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("anglesInDeg", anglesInDeg);
    table.put("angleOffset", angleOffset);
    table.put("accelX", accelX);
    table.put("accelY", accelY);
    table.put("accelZ", accelZ);
    table.put("velX", velX);
    table.put("velY", velY);
    table.put("velZ", velZ);
  }

  @Override
  public void fromLog(LogTable table) {
    anglesInDeg = table.get("anglesInDeg", anglesInDeg);
    angleOffset = table.get("angleOffset", angleOffset);
    accelX = table.get("accelX", accelX);
    accelY = table.get("accelY", accelY);
    accelZ = table.get("accelZ", accelZ);
    velX = table.get("velX", velX);
    velY = table.get("velY", velY);
    velZ = table.get("velZ", velZ);
  }
}
