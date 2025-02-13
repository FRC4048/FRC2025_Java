package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.InputSource;
import frc.robot.utils.logging.subsystem.inputs.BooleanInput;
import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import org.littletonrobotics.junction.LogTable;

/**
 * Contains Inputs that could be logged for a motor
 *
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public class BuildableFolderMotorInputs<R> extends BuildableFolderInputs<R> {
  private final DoubleInput<R> encoderPosition;
  private final DoubleInput<R> encoderVelocity;
  private final DoubleInput<R> motorCurrent;
  private final DoubleInput<R> motorTemperature;
  private final BooleanInput<R> fwdLimit;
  private final BooleanInput<R> revLimit;

  public BuildableFolderMotorInputs(
      String key,
      DoubleInput<R> encoderPosition,
      DoubleInput<R> encoderVelocity,
      DoubleInput<R> motorCurrent,
      DoubleInput<R> motorTemperature,
      BooleanInput<R> fwdLimit,
      BooleanInput<R> revLimit) {
    super(key);
    this.encoderPosition = encoderPosition;
    this.encoderVelocity = encoderVelocity;
    this.motorCurrent = motorCurrent;
    this.motorTemperature = motorTemperature;
    this.fwdLimit = fwdLimit;
    this.revLimit = revLimit;
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
}
