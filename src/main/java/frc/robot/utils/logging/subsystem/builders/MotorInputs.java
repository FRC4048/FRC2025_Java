package frc.robot.utils.logging.subsystem.builders;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import frc.robot.utils.logging.subsystem.processors.MotorInputSource;
import org.littletonrobotics.junction.LogTable;

/** Contains Inputs that could be logged for a motor */
public class MotorInputs extends FolderInputs {
  private Double encoderPosition;
  private Double encoderVelocity;
  private Double motorCurrent;
  private Double motorTemperature;
  private Boolean fwdLimit;
  private Boolean revLimit;

  public MotorInputs(Builder<?> builder) {
    super(builder);
    this.encoderPosition = builder.logEncoderPosition ? 0.0 : null;
    this.encoderVelocity = builder.logEncoderVelocity ? 0.0 : null;
    this.motorCurrent = builder.logMotorCurrent ? 0.0 : null;
    this.motorTemperature = builder.logMotorTemperature ? 0.0 : null;
    this.fwdLimit = builder.logFwdLimit ? false : null;
    this.revLimit = builder.logRevLimit ? false : null;
  }

  @Override
  public void toLog(LogTable table) {
    // This is my first EVER if train
    if (encoderPosition != null) {
      table.put("encoderPosition", encoderPosition);
    }
    if (encoderVelocity != null) {
      table.put("encoderVelocity", encoderVelocity);
    }
    if (motorCurrent != null) {
      table.put("motorCurrent", motorCurrent);
    }
    if (motorTemperature != null) {
      table.put("motorTemperature", motorTemperature);
    }
    if (fwdLimit != null) {
      table.put("fwdLimit", fwdLimit);
    }
    if (revLimit != null) {
      table.put("revLimit", revLimit);
    }
  }

  @Override
  public void fromLog(LogTable table) {
    // This is my second EVER if train
    if (encoderPosition != null) {
      encoderPosition = table.get("encoderPosition", encoderPosition);
    }
    if (encoderVelocity != null) {
      encoderVelocity = table.get("encoderVelocity", encoderVelocity);
    }
    if (motorCurrent != null) {
      motorCurrent = table.get("motorCurrent", motorCurrent);
    }
    if (motorTemperature != null) {
      motorTemperature = table.get("motorTemperature", motorTemperature);
    }
    if (fwdLimit != null) {
      fwdLimit = table.get("fwdLimit", fwdLimit);
    }
    if (revLimit != null) {
      revLimit = table.get("revLimit", revLimit);
    }
  }

  @Override
  public boolean process(InputSource inputSource) {
    if (inputSource instanceof MotorInputSource motorInputSource) {
      if (encoderPosition != null) {
        encoderPosition = motorInputSource.getEncoderPosition();
      }
      if (encoderVelocity != null) {
        encoderVelocity = motorInputSource.getEncoderVelocity();
      }
      if (motorCurrent != null) {
        motorCurrent = motorInputSource.getMotorCurrent();
      }
      if (motorTemperature != null) {
        motorTemperature = motorInputSource.getMotorTemperature();
      }
      if (fwdLimit != null) {
        fwdLimit = motorInputSource.getFwdLimit();
      }
      if (revLimit != null) {
        revLimit = motorInputSource.getRevLimit();
      }
      return true;
    } else {
      DriverStation.reportError("InputSource must be of type MotorInputSource", true);
      return false;
    }
  }

  public Double getEncoderPosition() {
    return encoderPosition;
  }

  public Double getEncoderVelocity() {
    return encoderVelocity;
  }

  public Double getMotorCurrent() {
    return motorCurrent;
  }

  public Double getMotorTemperature() {
    return motorTemperature;
  }

  public Boolean getFwdLimit() {
    return fwdLimit;
  }

  public Boolean getRevLimit() {
    return revLimit;
  }

  public static class Builder<T extends Builder<T>> extends FolderInputs.Builder<T> {
    private boolean logEncoderPosition;
    private boolean logEncoderVelocity;
    private boolean logMotorCurrent;
    private boolean logMotorTemperature;
    private boolean logFwdLimit;
    private boolean logRevLimit;

    public Builder(String folder) {
      super(folder);
    }

    @Override
    public MotorInputs build() {
      return new MotorInputs(this);
    }

    @Override
    public T reset() {
      logEncoderPosition = false;
      logEncoderVelocity = false;
      logMotorCurrent = false;
      logMotorTemperature = false;
      logFwdLimit = false;
      logRevLimit = false;
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
          .fwdLimit();
    }
  }
}
