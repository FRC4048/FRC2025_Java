package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.builders.SteerMotorInputBuilder;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import frc.robot.utils.logging.subsystem.providers.SteerMotorInputProvider;
import org.littletonrobotics.junction.LogTable;

public class SteerMotorInputs extends MotorInputs {
  private boolean steerConnected;
  private Double encoderPosition;
  private Double encoderVelocity;
  private Double motorCurrent;
  private Double motorTemperature;
  private Double appliedOutput;
  private Boolean fwdLimit;
  private Boolean revLimit;
  private boolean logSteerConnected;

  public SteerMotorInputs(SteerMotorInputBuilder<?> builder) {
    super(builder);
    this.encoderPosition = builder.isLogEncoderPosition() ? 0.0 : null;
    this.encoderVelocity = builder.isLogEncoderVelocity() ? 0.0 : null;
    this.motorCurrent = builder.isLogMotorCurrent() ? 0.0 : null;
    this.motorTemperature = builder.isLogMotorTemperature() ? 0.0 : null;
    this.fwdLimit = builder.isLogFwdLimit() ? false : null;
    this.revLimit = builder.isLogRevLimit() ? false : null;
    this.appliedOutput = builder.isLogAppliedOutput() ? 0.0 : null;
    this.logSteerConnected = builder.isLogSteerConnected();
  }

  public void toLog(LogTable table) {
    super.toLog(table);
    if (logSteerConnected) {
      table.put("driveConnected", steerConnected);
    }
  }

  public void fromLog(LogTable table) {
    super.fromLog(table);
    if (logSteerConnected) {
      steerConnected = table.get("driveConnected", steerConnected);
    }
  }

  public boolean process(InputProvider inputProvider) {
    if (inputProvider instanceof SteerMotorInputProvider motorinputProvider) {
      if (logSteerConnected) {
        steerConnected = motorinputProvider.isSteerConnected();
      }
    }
    return super.process(inputProvider) && logSteerConnected;
  }

  public boolean isSteerConnected() {
    return steerConnected;
  }

  public void setSteerConnected(boolean steerConnected) {
    this.steerConnected = steerConnected;
  }

  public void setEncoderPosition(double encoderPosition) {
    this.encoderPosition = encoderPosition;
  }

  public void setEncoderVelocity(double encoderVelocity) {
    this.encoderVelocity = encoderVelocity;
  }

  public void setMotorCurrent(double motorCurrent) {
    this.motorCurrent = motorCurrent;
  }

  public void setMotorTemperature(double motorTemperature) {
    this.motorTemperature = motorTemperature;
  }

  public void setAppliedOutput(double appliedOutput) {
    this.appliedOutput = appliedOutput;
  }

  public void setFwdLimit(boolean fwdLimit) {
    this.fwdLimit = fwdLimit;
  }

  public void setRevLimit(boolean revLimit) {
    this.revLimit = revLimit;
  }
}
