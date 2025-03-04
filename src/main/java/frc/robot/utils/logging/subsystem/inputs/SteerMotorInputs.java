package frc.robot.utils.logging.subsystem.inputs;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.logging.subsystem.builders.SteerMotorInputBuilder;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import frc.robot.utils.logging.subsystem.providers.SteerMotorInputProvider;
import org.littletonrobotics.junction.LogTable;

public class SteerMotorInputs extends FolderInputs {
  private boolean steerConnected;
  private Double encoderPosition;
  private Double encoderVelocity;
  private Double motorCurrent;
  private Double motorTemperature;
  private Double appliedOutput;
  private Boolean fwdLimit;
  private Boolean revLimit;
  private double[] odometryTimestamps;
  private Rotation2d[] odometryTurnPositions;
  private final SteerMotorInputBuilder<?> builder;

  public SteerMotorInputs(SteerMotorInputBuilder<?> builder) {
    super(builder.getFolder());
    this.builder = builder;
    this.encoderPosition = builder.isLogEncoderPosition() ? 0.0 : null;
    this.encoderVelocity = builder.isLogEncoderVelocity() ? 0.0 : null;
    this.motorCurrent = builder.isLogMotorCurrent() ? 0.0 : null;
    this.motorTemperature = builder.isLogMotorTemperature() ? 0.0 : null;
    this.fwdLimit = builder.isLogFwdLimit() ? false : null;
    this.revLimit = builder.isLogRevLimit() ? false : null;
    this.appliedOutput = builder.isLogAppliedOutput() ? 0.0 : null;
    this.odometryTimestamps = builder.isLogOdometryTimestamps() ? new double[] {} : null;
    this.odometryTurnPositions = builder.isLogOdometryTurnPositions() ? new Rotation2d[] {} : null;
  }

  @Override
  public void toLog(LogTable table) {
    if (builder.isLogSteerConnected()) {
      table.put("steerConnected", steerConnected);
    }
    if (builder.isLogOdometryTimestamps()) {
      table.put("odometryTimestamps", odometryTimestamps);
    }
    if (builder.isLogOdometryTurnPositions()) {
      table.put("odometryTurnPositions", odometryTurnPositions);
    }
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
    if (builder.isLogAppliedOutput()) {
      table.put("appliedOutput", appliedOutput);
    }
  }

  public void fromLog(LogTable table) {
    if (builder.isLogSteerConnected()) {
      steerConnected = table.get("steerConnected", steerConnected);
    }
    if (builder.isLogOdometryTimestamps()) {
      odometryTimestamps = table.get("odometryTimestamps", odometryTimestamps);
    }
    if (builder.isLogOdometryTurnPositions()) {
      odometryTurnPositions = table.get("odometryTurnPositions", odometryTurnPositions);
    }
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
    if (builder.isLogAppliedOutput()) {
      appliedOutput = table.get("appliedOutput", appliedOutput);
    }
  }

  public boolean process(InputProvider inputProvider) {
    if (inputProvider instanceof SteerMotorInputProvider motorinputProvider) {
      if (builder.isLogSteerConnected()) {
        steerConnected = motorinputProvider.isSteerConnected();
      }
      if (builder.isLogOdometryTimestamps()) {
        odometryTimestamps = motorinputProvider.getOdometryTimestamps();
      }
      if (builder.isLogOdometryTurnPositions()) {
        odometryTurnPositions = motorinputProvider.getOdometryTurnPositions();
      }
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
      if (builder.isLogAppliedOutput()) {
        appliedOutput = motorinputProvider.getAppliedOutput();
      }
      return true;
    } else {
      DriverStation.reportError("inputProvider must be of type MotorinputProvider", true);
      return false;
    }
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

  public void setOdometryTimestamps(double[] odometryTimestamps) {
    this.odometryTimestamps = odometryTimestamps;
  }

  public void setOdometryTurnPositions(Rotation2d[] odometryTurnPositions) {
    this.odometryTurnPositions = odometryTurnPositions;
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

  public Double getAppliedOutput() {
    return appliedOutput;
  }
}
