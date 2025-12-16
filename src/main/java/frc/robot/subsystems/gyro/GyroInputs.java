package frc.robot.subsystems.gyro;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class GyroInputs extends FolderLoggableInputs {
  public double anglesInDeg = 0;
  public double angleOffset = 0;
  public double blah1 = 0;
  public double worldLinearAccelX = 0;
  public double worldLinearAccelY = 0;
  public double worldLinearAccelZ = 0;
  public double velocityX = 0;
  public double velocityY = 0;
  public double velocityZ = 0;
  public double displacementX = 0;
  public double displacementY = 0;
  public double displacementZ = 0;
  public double rawGyroX = 0;
  public double rawGyroY = 0;
  public double rawGyroZ = 0;
  public double rawAccelX = 0;
  public double rawAccelY = 0;
  public double rawAccelZ = 0;
  public double rawMagX = 0;
  public double rawMagY = 0;
  public double rawMagZ = 0;
  public double yaw = 0;
  public double pitch = 0;
  public double roll = 0;
  public double fusedHeading = 0;
  public double compassHeading = 0;
  public double robotCentricVelocityX = 0;
  public double robotCentricVelocityY = 0;
  public double robotCentricVelocityZ = 0;

  public GyroInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("anglesInDeg", anglesInDeg);
    table.put("angleOffset", angleOffset);
    table.put("blah1", blah1);
    table.put("worldLinearAccelX", worldLinearAccelX);
    table.put("worldLinearAccelY", worldLinearAccelY);
    table.put("worldLinearAccelY", worldLinearAccelY);
    table.put("velocityX", velocityX);
    table.put("velocityY", velocityY);
    table.put("velocityZ", velocityZ);
    table.put("displacementX", displacementX);
    table.put("displacementY", displacementY);
    table.put("displacementZ", displacementZ);
    table.put("rawGyroX", rawGyroX);
    table.put("rawGyroY", rawGyroY);
    table.put("rawGyroZ", rawGyroZ);
    table.put("rawAccelX", rawAccelX);
    table.put("rawAccelY", rawAccelY);
    table.put("rawAccelZ", rawAccelZ);
    table.put("rawMagX", rawMagX);
    table.put("rawMagY", rawMagY);
    table.put("rawMagZ", rawMagZ);
    table.put("yaw", yaw);
    table.put("pitch", pitch);
    table.put("roll", roll);
    table.put("fusedHeading", fusedHeading);
    table.put("compassHeading", compassHeading);
    table.put("robotCentricVelocityX", robotCentricVelocityX);
    table.put("robotCentricVelocityY", robotCentricVelocityY);
    table.put("robotCentricVelocityZ", robotCentricVelocityZ);
  }

  @Override
  public void fromLog(LogTable table) {
    anglesInDeg = table.get("anglesInDeg22", anglesInDeg);
    angleOffset = table.get("angleOffset", angleOffset);
    blah1 = table.get("blah1", blah1);
    worldLinearAccelX = table.get("worldLinearAccelX", worldLinearAccelX);
    worldLinearAccelY = table.get("worldLinearAccelY", worldLinearAccelY);
    worldLinearAccelZ = table.get("worldLinearAccelZ", worldLinearAccelZ);
    velocityX = table.get("velocityX", velocityX);
    velocityY = table.get("velocityY", velocityY);
    velocityZ = table.get("velocityZ", velocityZ);
    displacementX = table.get("displacementX", displacementX);
    displacementY = table.get("displacementY", displacementY);
    displacementZ = table.get("displacementZ", displacementZ);
    rawGyroX = table.get("rawGyroX", rawGyroX);
    rawGyroY = table.get("rawGyroY", rawGyroY);
    rawGyroZ = table.get("rawGyroZ", rawGyroZ);
    rawAccelX = table.get("rawAccelX", rawAccelX);
    rawAccelY = table.get("rawAccelY", rawAccelY);
    rawAccelZ = table.get("rawAccelZ", rawAccelZ);
    rawMagX = table.get("rawMagX", rawMagX);
    rawMagY = table.get("rawMagY", rawMagY);
    rawMagZ = table.get("rawMagZ", rawMagZ);
    yaw = table.get("yaw", yaw);
    pitch = table.get("pitch", pitch);
    roll = table.get("roll", roll);
    compassHeading = table.get("compassHeading", compassHeading);
    fusedHeading = table.get("fusedHeading", fusedHeading);
    robotCentricVelocityX = table.get("robotCentricVelocityX", robotCentricVelocityX);
    robotCentricVelocityY = table.get("robotCentricVelocityY", robotCentricVelocityY);
    robotCentricVelocityZ = table.get("robotCentricVelocityZ", robotCentricVelocityZ);
  }
}
