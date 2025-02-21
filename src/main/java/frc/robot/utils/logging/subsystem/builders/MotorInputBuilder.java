package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class MotorInputBuilder<T extends MotorInputBuilder<T>> {
  private boolean logEncoderPosition;
  private boolean logEncoderVelocity;
  private boolean logMotorCurrent;
  private boolean logMotorTemperature;
  private boolean logFwdLimit;
  private boolean logRevLimit;
  private boolean logAppliedOutput;
  private final String folder;

  public MotorInputBuilder(String folder) {
    this.folder = folder;
  }

  T self() {
    return (T) this;
  }

  public MotorInputs build() {
    return new MotorInputs(this);
  }

  public T reset() {
    logEncoderPosition = false;
    logEncoderVelocity = false;
    logMotorCurrent = false;
    logMotorTemperature = false;
    logFwdLimit = false;
    logRevLimit = false;
    logAppliedOutput = false;
    return self();
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
        .motorAppliedOutput();
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
