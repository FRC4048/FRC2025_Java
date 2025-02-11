package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.BooleanInput;
import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import frc.robot.utils.logging.subsystem.processors.MotorInputProcessor;

/**
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware
 */
public class MotorInputBuilder<R> extends InputBuilder<R, BuildableFolderMotorInputs<R>> {
  protected DoubleInput<R> encoderPosition;
  protected DoubleInput<R> encoderVelocity;
  protected DoubleInput<R> motorCurrent;
  protected DoubleInput<R> motorTemperature;
  protected BooleanInput<R> fwdLimit;
  protected BooleanInput<R> revLimit;
  protected final MotorInputProcessor<R> inputProcessor;

  public MotorInputBuilder(String folder, MotorInputProcessor<R> inputProcessor) {
    super(folder);
    this.inputProcessor = inputProcessor;
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
    motorCurrent = new DoubleInput<>("motorCurrent", inputProcessor.currentFromSource());
    return this;
  }

  public MotorInputBuilder<R> motorTemperature() {
    motorCurrent =
        new DoubleInput<>("motorTemperature", inputProcessor.motorTemperatureFromSource());
    return this;
  }

  public MotorInputBuilder<R> encoderPosition() {
    encoderPosition =
        new DoubleInput<>("encoderPosition", inputProcessor.encoderPositionFromSource());
    return this;
  }

  public MotorInputBuilder<R> encoderVelocity() {
    encoderVelocity =
        new DoubleInput<>("encoderVelocity", inputProcessor.encoderVelocityFromSource());
    return this;
  }

  public MotorInputBuilder<R> fwdLimit() {
    fwdLimit = new BooleanInput<>("fwdLimit", inputProcessor.fwdLimitFromSource());
    return this;
  }

  public MotorInputBuilder<R> revLimit() {
    revLimit = new BooleanInput<>("revLimit", inputProcessor.revLimitFromSource());
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
}
