package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;

public class SteerMotorInputBuilder<T extends SteerMotorInputBuilder<T>>
    extends MotorInputBuilder<T> {
  private boolean logSteerConnected;
  private boolean logOdometryTimestamps;
  private boolean logOdometryTurnPositions;

  public SteerMotorInputBuilder(String folder) {
    super(folder);
  }

  public T reset() {
    super.reset();
    logSteerConnected = false;
    logOdometryTimestamps = false;
    logOdometryTurnPositions = false;
    return self();
  }

  public SteerMotorInputs build() {
    return new SteerMotorInputs(this);
  }

  public T steerConnected() {
    logSteerConnected = true;
    return self();
  }

  public T odometryTimestamps() {
    logOdometryTimestamps = true;
    return self();
  }

  public T odometryTurnPositions() {
    logOdometryTurnPositions = true;
    return self();
  }

  public boolean isLogSteerConnected() {
    return logSteerConnected;
  }

  public boolean isLogOdometryTimestamps() {
    return logOdometryTimestamps;
  }

  public boolean isLogOdometryTurnPositions() {
    return logOdometryTurnPositions;
  }
}
