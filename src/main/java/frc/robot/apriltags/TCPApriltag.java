package frc.robot.apriltags;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.LoggableIO;
import org.littletonrobotics.junction.Logger;

import java.util.Queue;

public class TCPApriltag implements LoggableIO<ApriltagInputs> {
  private final TCPApriltagServer server;

  public TCPApriltag() {
      server = new TCPApriltagServer(Constants.TCP_SERVER_PORT);
    server.start();
  }

  @Override
  public void updateInputs(ApriltagInputs inputs) {
    Queue<ApriltagReading> queue = server.flush();
    Logger.recordOutput("VisionMeasurementsThisTick", queue.size());
    inputs.posX = new double[queue.size()];
    inputs.posY = new double[queue.size()];
      inputs.poseYaw = new double[queue.size()];
      inputs.distanceToTag = new double[queue.size()];
      inputs.apriltagNumber = new int[queue.size()];
    inputs.serverTime = new double[queue.size()];
    inputs.timestamp = new double[queue.size()];
    for (int i = 0; i < queue.size(); i++) {
      ApriltagReading measurement = queue.poll();
      inputs.posX[i] = measurement.posX();
      inputs.posY[i] = measurement.posY();
        inputs.poseYaw[i] = measurement.poseYaw();
        inputs.distanceToTag[i] = measurement.distanceToTag();
        inputs.apriltagNumber[i] = measurement.apriltagNumber();
      inputs.timestamp[i] = measurement.latency();
      inputs.serverTime[i] = measurement.measurementTime();
    }
  }
}
