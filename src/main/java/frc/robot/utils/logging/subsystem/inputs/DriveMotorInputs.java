package frc.robot.utils.logging.subsystem.inputs;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.logging.subsystem.builders.DriveMotorInputBuilder;
import frc.robot.utils.logging.subsystem.providers.DriveModuleSimInputProvider;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import org.littletonrobotics.junction.LogTable;

public class DriveMotorInputs extends FolderInputs {
  private boolean driveConnected;
  private Double encoderPosition;
  private Double encoderVelocity;
  private Double motorCurrent;
  private Double motorTemperature;
  private Double appliedOutput;
  private Boolean fwdLimit;
  private Boolean revLimit;
  private double[] odometryTimestamps;
  private double[] odometryDrivePositionsRad;
  private final DriveMotorInputBuilder<?> builder;

  public DriveMotorInputs(DriveMotorInputBuilder<?> builder) {
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
    this.odometryDrivePositionsRad =
        builder.isLogOdometryDrivePositionsRad() ? new double[] {} : null;
  }

  public void toLog(LogTable table) {
    if (builder.isLogDriveConnected()) {
      table.put("driveConnected", driveConnected);
    }
    if (builder.isLogOdometryTimestamps()) {
      table.put("odometryTimestamps", odometryTimestamps);
    }
    if (builder.isLogOdometryDrivePositionsRad()) {
      table.put("odometryDrivePositionsRad", odometryDrivePositionsRad);
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
    if (builder.isLogDriveConnected()) {
      driveConnected = table.get("driveConnected", driveConnected);
    }
    if (builder.isLogOdometryTimestamps()) {
      odometryTimestamps = table.get("odometryTimestamps", odometryTimestamps);
    }
    if (builder.isLogOdometryDrivePositionsRad()) {
      odometryDrivePositionsRad = table.get("odometryDrivePositionsRad", odometryDrivePositionsRad);
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
    if (inputProvider instanceof DriveModuleSimInputProvider motorinputProvider) {
      if (builder.isLogDriveConnected()) {
        driveConnected = motorinputProvider.isDriveConnected();
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
