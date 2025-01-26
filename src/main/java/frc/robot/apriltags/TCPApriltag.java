package frc.robot.apriltags;

import frc.robot.utils.logging.LoggableIO;
import java.util.Queue;
import org.littletonrobotics.junction.Logger;

public class TCPApriltag implements LoggableIO<ApriltagInputs> {
  private static final int PORT = 5806;
  private final TCPApriltagServer server;

  public TCPApriltag() {
    server = new TCPApriltagServer(PORT);
    server.start();
  }

  @Override
  public void updateInputs(ApriltagInputs inputs) {
    Queue<ApriltagReading> queue = server.flush();
    Logger.recordOutput("VisionMeasurementsThisTick", queue.size());
    inputs.posX = new double[queue.size()];
    inputs.posY = new double[queue.size()];
    inputs.rotationDeg = new double[queue.size()];
    inputs.serverTime = new double[queue.size()];
    inputs.timestamp = new double[queue.size()];
    inputs.apriltagNumber = new int[queue.size()];
    for (int i = 0; i < queue.size(); i++) {
      ApriltagReading measurement = queue.poll();
      inputs.apriltagNumber[i] = 1;
      inputs.posX[i] = measurement.posX();
      inputs.posY[i] = measurement.posY();
      inputs.rotationDeg[i] = measurement.rotationDeg();
      inputs.timestamp[i] = measurement.latency();
      inputs.serverTime[i] = measurement.measurementTime();
    }
  }
}
