package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.DriveMotorInputs;

public class DriveMotorInputBuilder<T extends MotorInputBuilder<T>> extends MotorInputBuilder<T> {
  private boolean logDriveConnected;
  private boolean logOdometryTimestamps;
  private boolean logOdometryDrivePositionsRad;

  public DriveMotorInputBuilder(String folder) {
    super(folder);
  }

  public T reset() {
    super.reset();
    logDriveConnected = false;
    logOdometryTimestamps = false;
    logOdometryDrivePositionsRad = false;
    return self();
  }

  public DriveMotorInputs build() {
    return new DriveMotorInputs(this);
  }

  public T driveConnected() {
    logDriveConnected = true;
    return self();
  }

  public T odometryTimestamps() {
    logOdometryTimestamps = true;
    return self();
  }

  public T odometryDrivePositionsRad() {
    logOdometryDrivePositionsRad = true;
    return self();
  }

  public boolean isLogDriveConnected() {
    return logDriveConnected;
  }

  public boolean isLogOdometryTimestamps() {
    return logOdometryTimestamps;
  }

  public boolean isLogOdometryDrivePositionsRad() {
    return logOdometryDrivePositionsRad;
  }
}
