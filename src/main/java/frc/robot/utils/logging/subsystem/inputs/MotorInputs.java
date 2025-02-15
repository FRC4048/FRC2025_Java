package frc.robot.utils.logging.subsystem.inputs;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import frc.robot.utils.logging.subsystem.providers.MotorInputProvider;
import org.littletonrobotics.junction.LogTable;

/** Contains Inputs that could be logged for a motor */
public class MotorInputs extends FolderInputs {
  private Double encoderPosition;
  private Double encoderVelocity;
  private Double motorCurrent;
  private Double motorTemperature;
  private Boolean fwdLimit;
  private Boolean revLimit;
  private final MotorInputBuilder<?> builder;

    public MotorInputs(MotorInputBuilder<?> builder) {
    super(builder.getFolder());
    this.encoderPosition = builder.isLogEncoderPosition() ? 0.0 : null;
    this.encoderVelocity = builder.isLogEncoderVelocity() ? 0.0 : null;
    this.motorCurrent = builder.isLogMotorCurrent() ? 0.0 : null;
    this.motorTemperature = builder.isLogMotorTemperature() ? 0.0 : null;
    this.fwdLimit = builder.isLogFwdLimit() ? false : null;
    this.revLimit = builder.isLogRevLimit() ? false : null;
    this.builder = builder;
    }

  @Override
  public void toLog(LogTable table) {
    // This is my first EVER if train
    if (builder.isLogEncoderPosition()) {
      table.put("encoderPosition", encoderPosition);
    }
    if (builder.isLogEncoderVelocity()) {
      table.put("encoderVelocity", encoderVelocity);
    }
    if (builder.isLogMotorCurrent()) {
      table.put("motorCurrent", motorCurrent);
    }
    if (builder.isLogMotorTemperature()) {
      table.put("motorTemperature", motorTemperature);
    }
    if (builder.isLogFwdLimit()) {
      table.put("fwdLimit", fwdLimit);
    }
    if (builder.isLogRevLimit()) {
      table.put("revLimit", revLimit);
    }
  }

  @Override
  public void fromLog(LogTable table) {
    // This is my second EVER if train
    if (builder.isLogEncoderPosition()) {
      encoderPosition = table.get("encoderPosition", encoderPosition);
    }
    if (builder.isLogEncoderVelocity()) {
      encoderVelocity = table.get("encoderVelocity", encoderVelocity);
    }
    if (builder.isLogMotorCurrent()) {
      motorCurrent = table.get("motorCurrent", motorCurrent);
    }
    if (builder.isLogMotorTemperature()) {
      motorTemperature = table.get("motorTemperature", motorTemperature);
    }
    if (builder.isLogFwdLimit()) {
      fwdLimit = table.get("fwdLimit", fwdLimit);
    }
    if (builder.isLogRevLimit()) {
      revLimit = table.get("revLimit", revLimit);
    }
  }

  @Override
  public boolean process(InputProvider inputProvider) {
    if (inputProvider instanceof MotorInputProvider motorinputProvider) {
      if (builder.isLogEncoderPosition()) {
        encoderPosition = motorinputProvider.getEncoderPosition();
      }
      if (builder.isLogEncoderVelocity()) {
        encoderVelocity = motorinputProvider.getEncoderVelocity();
      }
      if (builder.isLogMotorCurrent()) {
        motorCurrent = motorinputProvider.getMotorCurrent();
      }
      if (builder.isLogMotorTemperature()) {
        motorTemperature = motorinputProvider.getMotorTemperature();
      }
      if (builder.isLogFwdLimit()) {
        fwdLimit = motorinputProvider.getFwdLimit();
      }
      if (builder.isLogRevLimit()) {
        revLimit = motorinputProvider.getRevLimit();
      }
      return true;
    } else {
      DriverStation.reportError("inputProvider must be of type MotorinputProvider", true);
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
}
