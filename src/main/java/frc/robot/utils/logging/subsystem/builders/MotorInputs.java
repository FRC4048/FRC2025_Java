package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.BooleanInput;
import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import frc.robot.utils.logging.subsystem.processors.MotorInputProcessor;
import org.littletonrobotics.junction.LogTable;

/**
 * Contains Inputs that could be logged for a motor
 * @param <R> Hardware class that is used by an {@link
 *     frc.robot.utils.logging.subsystem.processors.InputSource} to pull data from hardware.
 */
public class MotorInputs<R> extends FolderInputs<R> {
  private final DoubleInput<R> encoderPosition;
  private final DoubleInput<R> encoderVelocity;
  private final DoubleInput<R> motorCurrent;
  private final DoubleInput<R> motorTemperature;
  private final BooleanInput<R> fwdLimit;
  private final BooleanInput<R> revLimit;

  public MotorInputs(Builder<R, ?> builder) {
    super(builder);
    this.encoderPosition = builder.encoderPosition;
    this.encoderVelocity = builder.encoderVelocity;
    this.motorCurrent = builder.motorCurrent;
    this.motorTemperature = builder.motorTemperature;
    this.fwdLimit = builder.fwdLimit;
    this.revLimit = builder.revLimit;
  }

  @Override
  public void toLog(LogTable table) {
    // This is my first EVER if train
    if (encoderPosition != null) {
      encoderPosition.toLog(table);
    }
    if (encoderVelocity != null) {
      encoderVelocity.toLog(table);
    }
    if (motorCurrent != null) {
      motorCurrent.toLog(table);
    }
    if (motorTemperature != null) {
      motorTemperature.toLog(table);
    }
    if (fwdLimit != null) {
      fwdLimit.toLog(table);
    }
    if (revLimit != null) {
      revLimit.toLog(table);
    }
  }

  @Override
  public void fromLog(LogTable table) {
    // This is my second EVER if train
    if (encoderPosition != null) {
      encoderPosition.fromLog(table);
    }
    if (encoderVelocity != null) {
      encoderVelocity.fromLog(table);
    }
    if (motorCurrent != null) {
      motorCurrent.fromLog(table);
    }
    if (motorTemperature != null) {
      motorTemperature.fromLog(table);
    }
    if (fwdLimit != null) {
      fwdLimit.fromLog(table);
    }
    if (revLimit != null) {
      revLimit.fromLog(table);
    }
  }

  @Override
  public void process(R source) {
    // This is my third EVER if else train
    if (encoderPosition != null) {
      encoderPosition.process(source);
    }
    if (encoderVelocity != null) {
      encoderVelocity.process(source);
    }
    if (motorCurrent != null) {
      motorCurrent.process(source);
    }
    if (motorTemperature != null) {
      motorTemperature.process(source);
    }
    if (fwdLimit != null) {
      fwdLimit.process(source);
    }
    if (revLimit != null) {
      revLimit.process(source);
    }
  }

  public double getEncoderPosition() {
    return encoderPosition.getValue();
  }

  public double getEncoderVelocity() {
    return encoderVelocity.getValue();
  }

  public double getMotorCurrent() {
    return motorCurrent.getValue();
  }

  public double getMotorTemperature() {
    return motorTemperature.getValue();
  }

  public boolean fwdLimit() {
    return fwdLimit.getValue();
  }

  public boolean revLimit() {
    return revLimit.getValue();
  }

  public static class Builder<R, T extends Builder<R, T>> extends FolderInputs.Builder<R, T> {
    protected final MotorInputProcessor<R> inputProcessor;
    private DoubleInput<R> encoderPosition;
    private DoubleInput<R> encoderVelocity;
    private DoubleInput<R> motorCurrent;
    private DoubleInput<R> motorTemperature;
    private BooleanInput<R> fwdLimit;
    private BooleanInput<R> revLimit;

    public Builder(String folder, MotorInputProcessor<R> inputProcessor) {
      super(folder);
      this.inputProcessor = inputProcessor;
    }

    @Override
    public MotorInputs<R> build() {
      return new MotorInputs<>(this);
    }

    @Override
    public T reset() {
      encoderPosition = null;
      encoderVelocity = null;
      motorCurrent = null;
      motorTemperature = null;
      fwdLimit = null;
      revLimit = null;
      return self();
    }

    public T motorCurrent() {
      motorCurrent = new DoubleInput<>("motorCurrent", inputProcessor.currentFromSource());
      return self();
    }

    public T motorTemperature() {
      motorCurrent =
          new DoubleInput<>("motorTemperature", inputProcessor.motorTemperatureFromSource());
      return self();
    }

    public T encoderPosition() {
      encoderPosition =
          new DoubleInput<>("encoderPosition", inputProcessor.encoderPositionFromSource());
      return self();
    }

    public T encoderVelocity() {
      encoderVelocity =
          new DoubleInput<>("encoderVelocity", inputProcessor.encoderVelocityFromSource());
      return self();
    }

    public T fwdLimit() {
      fwdLimit = new BooleanInput<>("fwdLimit", inputProcessor.fwdLimitFromSource());
      return self();
    }

    public T revLimit() {
      revLimit = new BooleanInput<>("revLimit", inputProcessor.revLimitFromSource());
      return self();
    }

    public T addEncoder() {
      return encoderPosition().encoderVelocity();
    }

    public T addStatus() {
      return motorCurrent().motorTemperature();
    }

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
