package frc.robot.apriltags;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ApriltagInputs implements LoggableInputs {
  public double[] timestamp = new double[0];
  public double[] serverTime = new double[0];
  public double[] posX = new double[0];
  public double[] posY = new double[0];
  public double[] poseYaw = new double[0];
  public double[] distanceToTag = new double[0];
  public int[] apriltagNumber = new int[0];

  @Override
  public void toLog(LogTable table) {
    table.put("timestamp", timestamp);
    table.put("serverTime", serverTime);
    table.put("posX", posX);
    table.put("posY", posY);
    table.put("poseYaw", poseYaw);
    table.put("distanceToTag", distanceToTag);
    table.put("apriltagNumber", apriltagNumber);
  }

  @Override
  public void fromLog(LogTable table) {
    timestamp = table.get("timestamp", timestamp);
    serverTime = table.get("serverTime", serverTime);
    posX = table.get("posX", posX);
    posY = table.get("posY", posY);
    poseYaw = table.get("poseYaw", poseYaw);
    distanceToTag = table.get("distanceToTag", distanceToTag);
    apriltagNumber = table.get("apriltagNumber", apriltagNumber);
  }
}
