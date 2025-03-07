package frc.robot.subsystems.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class GyroInputs extends FolderLoggableInputs {
  public double anglesInDeg = 0;
  public double angleOffset = 0;
  public double angularVelocityInDeg = 0;
  public boolean connected = false;
  public double[] odometryYawTimestamps = new double[] {};
  public Rotation2d[] odometryYawPositions = new Rotation2d[] {};

  public GyroInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("anglesInDeg", anglesInDeg);
    table.put("angleOffset", angleOffset);
    table.put("angleVelocityInDeg", angularVelocityInDeg);
    table.put("connected", connected);
    table.put("odometryYawTimestamps", odometryYawTimestamps);
    table.put("odometryYawPositions", odometryYawPositions);
  }

  @Override
  public void fromLog(LogTable table) {
    anglesInDeg = table.get("anglesInDeg", anglesInDeg);
    angleOffset = table.get("angleOffset", angleOffset);
    angularVelocityInDeg = table.get("angleVelocityInDeg", angularVelocityInDeg);
    connected = table.get("connected", connected);
    odometryYawTimestamps = table.get("odometryYawTimestamps", odometryYawTimestamps);
    odometryYawPositions = table.get("odometryYawPositions", odometryYawPositions);
  }
}
