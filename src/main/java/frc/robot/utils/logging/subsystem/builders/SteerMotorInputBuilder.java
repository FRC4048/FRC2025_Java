package frc.robot.utils.logging.subsystem.builders;

public class SteerMotorInputBuilder<T extends SteerMotorInputBuilder<T>>
    extends MotorInputBuilder<T> {
  private boolean logSteerConnected;

  public SteerMotorInputBuilder(String folder) {
    super(folder);
  }

  public T reset() {
    super.reset();
    logSteerConnected = false;
    return self();
  }

  public T steerConnected() {
    logSteerConnected = true;
    return self();
  }

  public boolean isLogSteerConnected() {
    return logSteerConnected;
  }
}
