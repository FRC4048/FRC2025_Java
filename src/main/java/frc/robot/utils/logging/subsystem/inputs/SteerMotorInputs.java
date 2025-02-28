package frc.robot.utils.logging.subsystem.inputs;

import edu.wpi.first.math.geometry.Rotation2d;
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
  private boolean logOdometryTimestamps;
  private boolean logOdometryTurnPositions;
  private double[] odometryTimestamps;
  private Rotation2d[] odometryTurnPositions;

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
    this.logOdometryTimestamps = builder.isLogOdometryTimestamps();
    this.logOdometryTurnPositions = builder.isLogOdometryTurnPositions();
    this.odometryTimestamps = builder.isLogOdometryTimestamps() ? new double[] {} : null;
    this.odometryTurnPositions = builder.isLogOdometryTurnPositions() ? new Rotation2d[] {} : null;
  }

  public void toLog(LogTable table) {
    super.toLog(table);
    if (logSteerConnected) {
      table.put("steerConnected", steerConnected);
    }
    if (logOdometryTimestamps) {
      table.put("odometryTimestamps", odometryTimestamps);
    }
    if (logOdometryTimestamps) {
      table.put("odometryTurnPositions", odometryTurnPositions);
    }
  }

  public void fromLog(LogTable table) {
    super.fromLog(table);
    if (logSteerConnected) {
      steerConnected = table.get("steerConnected", steerConnected);
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

  public void setOdometryTimestamps(double[] odometryTimestamps) {
    this.odometryTimestamps = odometryTimestamps;
  }

  public void setOdometryTurnPositions(Rotation2d[] odometryTurnPositions) {
    this.odometryTurnPositions = odometryTurnPositions;
  }
}
