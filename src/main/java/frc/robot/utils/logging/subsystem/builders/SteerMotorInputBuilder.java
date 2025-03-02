package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;

public class SteerMotorInputBuilder<T extends SteerMotorInputBuilder<T>> {
  private boolean logSteerConnected;
  private boolean logOdometryTimestamps;
  private boolean logOdometryTurnPositions;
  private final String folder;
  private boolean logEncoderPosition;
  private boolean logEncoderVelocity;
  private boolean logMotorCurrent;
  private boolean logMotorTemperature;
  private boolean logFwdLimit;
  private boolean logRevLimit;
  private boolean logAppliedOutput;

  public SteerMotorInputBuilder(String folder) {
    this.folder = folder;
  }

  T self() {
    return (T) this;
  }

  public T reset() {
    logSteerConnected = false;
    logOdometryTimestamps = false;
    logOdometryTurnPositions = false;
    logEncoderPosition = false;
    logEncoderVelocity = false;
    logMotorCurrent = false;
    logMotorTemperature = false;
    logFwdLimit = false;
    logRevLimit = false;
    logAppliedOutput = false;
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

  public T motorAppliedOutput() {
    logAppliedOutput = true;
    return self();
  }

  public T motorCurrent() {
    logMotorCurrent = true;
    return self();
  }

  public T motorTemperature() {
    logMotorTemperature = true;
    return self();
  }

  public T encoderPosition() {
    logEncoderPosition = true;
    return self();
  }

  public T encoderVelocity() {
    logEncoderVelocity = true;
    return self();
  }

  public T fwdLimit() {
    logFwdLimit = true;
    return self();
  }

  public T revLimit() {
    logRevLimit = true;
    return self();
  }

  /**
   * Adds {@link #encoderPosition()} and {@link #encoderVelocity()}
   *
   * @return the builder
   */
  public T addEncoder() {
    return encoderPosition().encoderVelocity();
  }

  /**
   * Adds {@link #motorCurrent()} and {@link #motorTemperature()}
   *
   * @return the builder
   */
  public T addStatus() {
    return motorCurrent().motorTemperature();
  }

  /**
   * Adds {@link #fwdLimit()} and {@link #revLimit()}
   *
   * @return the builder
   */
  public T addLimits() {
    return fwdLimit().revLimit();
  }

  public T addAll() {
    return motorCurrent()
        .motorTemperature()
        .encoderPosition()
        .encoderVelocity()
        .revLimit()
        .fwdLimit()
        .motorAppliedOutput()
        .steerConnected()
        .odometryTimestamps()
        .odometryTurnPositions();
  }

  public boolean isLogEncoderPosition() {
    return logEncoderPosition;
  }

  public boolean isLogEncoderVelocity() {
    return logEncoderVelocity;
  }

  public boolean isLogMotorCurrent() {
    return logMotorCurrent;
  }

  public boolean isLogMotorTemperature() {
    return logMotorTemperature;
  }

  public boolean isLogFwdLimit() {
    return logFwdLimit;
  }

  public boolean isLogRevLimit() {
    return logRevLimit;
  }

  public boolean isLogAppliedOutput() {
    return logAppliedOutput;
  }

  public String getFolder() {
    return folder;
  }
}
