package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.builders.DriveMotorInputBuilder;
import frc.robot.utils.logging.subsystem.providers.DriveMotorInputProvider;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import org.littletonrobotics.junction.LogTable;

public class DriveMotorInputs extends MotorInputs {
  private boolean driveConnected;
  private Double encoderPosition;
  private Double encoderVelocity;
  private Double motorCurrent;
  private Double motorTemperature;
  private Double appliedOutput;
  private Boolean fwdLimit;
  private Boolean revLimit;
  private boolean logDriveConnected;
  private double[] odometryTimestamps;
  private double[] odometryDrivePositionsRad;
  private boolean logOdometryTimestamps;
  private boolean logOdometryDrivePositionsRad;

  public DriveMotorInputs(DriveMotorInputBuilder<?> builder) {
    super(builder);
    this.encoderPosition = builder.isLogEncoderPosition() ? 0.0 : null;
    this.encoderVelocity = builder.isLogEncoderVelocity() ? 0.0 : null;
    this.motorCurrent = builder.isLogMotorCurrent() ? 0.0 : null;
    this.motorTemperature = builder.isLogMotorTemperature() ? 0.0 : null;
    this.fwdLimit = builder.isLogFwdLimit() ? false : null;
    this.revLimit = builder.isLogRevLimit() ? false : null;
    this.appliedOutput = builder.isLogAppliedOutput() ? 0.0 : null;
    this.logOdometryTimestamps = builder.isLogOdometryTimestamps();
    this.logOdometryDrivePositionsRad = builder.isLogOdometryDrivePositionsRad();
    this.odometryTimestamps = builder.isLogOdometryTimestamps() ? new double[] {} : null;
    this.odometryDrivePositionsRad =
        builder.isLogOdometryDrivePositionsRad() ? new double[] {} : null;
  }

  public void toLog(LogTable table) {
    super.toLog(table);
    if (logDriveConnected) {
      table.put("driveConnected", driveConnected);
    }
    if (logOdometryTimestamps) {
      table.put("odometryTimestamps", odometryTimestamps);
    }
    if (logOdometryDrivePositionsRad) {
      table.put("odometryDrivePositionsRad", odometryDrivePositionsRad);
    }
  }

  public void fromLog(LogTable table) {
    super.fromLog(table);
    if (logDriveConnected) {
      driveConnected = table.get("driveConnected", driveConnected);
    }
    if (logOdometryTimestamps) {
      odometryTimestamps = table.get("odometryTimestamps", odometryTimestamps);
    }
    if (logOdometryDrivePositionsRad) {
      odometryDrivePositionsRad = table.get("odometryDrivePositionsRad", odometryDrivePositionsRad);
    }
  }

  public boolean process(InputProvider inputProvider) {
    if (inputProvider instanceof DriveMotorInputProvider motorinputProvider) {
      if (logDriveConnected) {
        driveConnected = motorinputProvider.isDriveConnected();
      }
    }
    return super.process(inputProvider) && logDriveConnected;
  }

  public boolean isDriveConnected() {
    return driveConnected;
  }

  public void setDriveConnected(boolean driveConnected) {
    this.driveConnected = driveConnected;
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

  public void setOdometryDrivePositionsRad(double[] odometryDrivePositionsRad) {
    this.odometryDrivePositionsRad = odometryDrivePositionsRad;
  }
}
