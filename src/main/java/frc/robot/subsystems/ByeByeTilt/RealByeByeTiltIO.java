// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.ByeByeTilt;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.constants.Constants;

/** Add your docs here. */
public class RealByeByeTiltIO implements ByeByeTiltIO {
  private final WPI_TalonSRX removerTiltMotor; // SnowblowerMotor

  public RealByeByeTiltIO() {

    this.removerTiltMotor = new WPI_TalonSRX(Constants.ALGAE_BYEBYE_TILT_ID);
    configureMotor();
    resetEncoder();
  }

  public void configureMotor() {
    this.removerTiltMotor.setNeutralMode(NeutralMode.Brake);
    this.removerTiltMotor.configForwardLimitSwitchSource(
        LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    this.removerTiltMotor.configReverseLimitSwitchSource(
        LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
  }

  @Override
  public void setSpeed(double speed) {
    this.removerTiltMotor.set(speed);
  }

  @Override
  public void stopMotors() {
    this.removerTiltMotor.set(0);
  }

  @Override
  public void resetEncoder() {
    this.removerTiltMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void updateInputs(ByeByeTiltInputs inputs) {
    inputs.forwardLimitSwitchState =
        removerTiltMotor.getSensorCollection().isFwdLimitSwitchClosed();
    inputs.backLimitSwitchState = removerTiltMotor.getSensorCollection().isRevLimitSwitchClosed();
    inputs.tiltMotorEncoderPosition = removerTiltMotor.getSelectedSensorPosition();
  }
}
