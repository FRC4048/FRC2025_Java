package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.InputBuilder;
import frc.robot.utils.logging.subsystem.InputSource;
import frc.robot.utils.logging.subsystem.inputs.BooleanInput;
import frc.robot.utils.logging.subsystem.inputs.DoubleInput;

/**
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware
 */
public abstract class MotorInputBuilder<R> extends InputBuilder<R, BuildableFolderMotorInputs<R>> {
  private DoubleInput<R> encoderPosition;
  private DoubleInput<R> encoderVelocity;
  private DoubleInput<R> motorCurrent;
  private DoubleInput<R> motorTemperature;
  private BooleanInput<R> fwdLimit;
  private BooleanInput<R> revLimit;

  public MotorInputBuilder(String folder) {
    super(folder);
  }

  @Override
  public BuildableFolderMotorInputs<R> build() {
    return new BuildableFolderMotorInputs<>(
        folder,
        encoderPosition,
        encoderVelocity,
        motorCurrent,
        motorTemperature,
        fwdLimit,
        revLimit);
  }

  public MotorInputBuilder<R> motorCurrent() {
    motorCurrent = new DoubleInput<>("motorCurrent", currentFromSource());
    return this;
  }

  public MotorInputBuilder<R> motorTemperature() {
    motorCurrent = new DoubleInput<>("motorTemperature", motorTemperatureFromSource());
    return this;
  }

  public MotorInputBuilder<R> encoderPosition() {
    encoderPosition = new DoubleInput<>("encoderPosition", encoderPositionFromSource());
    return this;
  }

  public MotorInputBuilder<R> encoderVelocity() {
    encoderVelocity = new DoubleInput<>("encoderVelocity", encoderVelocityFromSource());
    return this;
  }

  public MotorInputBuilder<R> fwdLimit() {
    fwdLimit = new BooleanInput<>("fwdLimit", fwdLimitFromSource());
    return this;
  }

  public MotorInputBuilder<R> revLimit() {
    revLimit = new BooleanInput<>("revLimit", revLimitFromSource());
    return this;
  }

  public MotorInputBuilder<R> addEncoder() {
    return encoderPosition().encoderVelocity();
  }

  public MotorInputBuilder<R> addStatus() {
    return motorCurrent().motorTemperature();
  }

  public MotorInputBuilder<R> addLimits() {
    return fwdLimit().revLimit();
  }

  public MotorInputBuilder<R> addAll() {
    return motorCurrent()
        .motorTemperature()
        .encoderPosition()
        .encoderVelocity()
        .revLimit()
        .fwdLimit();
  }

  public MotorInputBuilder<R> reset() {
    encoderPosition = null;
    encoderVelocity = null;
    motorCurrent = null;
    motorTemperature = null;
    fwdLimit = null;
    revLimit = null;
    return this;
  }

  protected abstract InputSource<Double, R> currentFromSource();

  protected abstract InputSource<Double, R> motorTemperatureFromSource();

  protected abstract InputSource<Double, R> encoderPositionFromSource();

  protected abstract InputSource<Double, R> encoderVelocityFromSource();

  protected abstract InputSource<Boolean, R> fwdLimitFromSource();

  protected abstract InputSource<Boolean, R> revLimitFromSource();
}
