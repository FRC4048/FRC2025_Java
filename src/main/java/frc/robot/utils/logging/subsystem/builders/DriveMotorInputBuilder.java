package frc.robot.utils.logging.subsystem.builders;

public class DriveMotorInputBuilder<T extends DriveMotorInputBuilder<T>>
    extends MotorInputBuilder<T> {
  private boolean logDriveConnected;

  public DriveMotorInputBuilder(String folder) {
    super(folder);
  }

  public T reset() {
    super.reset();
    logDriveConnected = false;
    return self();
  }

  public T driveConnected() {
    logDriveConnected = true;
    return self();
  }

  public boolean isLogDriveConnected() {
    return logDriveConnected;
  }
}
