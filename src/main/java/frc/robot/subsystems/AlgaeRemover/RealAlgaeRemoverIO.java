// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgaeRemover;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.constants.Constants;

/** Add your docs here. */
public class RealAlgaeRemoverIO implements AlgaeRemoverIO{
private final WPI_TalonSRX removerMotor;
private final WPI_TalonSRX removerTiltMotor; //SnowblowerMotor

public RealAlgaeRemoverIO(){
this.removerMotor = new WPI_TalonSRX(Constants.ALGAE_REMOVER_SPINING_ID);
this.removerTiltMotor = new WPI_TalonSRX(Constants.ALGAE_REMOVER_TILT_ID);
configureMotor();
resetTiltEncoder();
}
private void configureMotor(){
  this.removerMotor.setNeutralMode(NeutralMode.Brake);
  this.removerTiltMotor.setNeutralMode(NeutralMode.Brake);
  this.removerTiltMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
  this.removerTiltMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
}

@Override
public void setRemoverSpeed(double speed){
this.removerMotor.set(speed);
}

@Override
public void setTiltMotorSpeed(double tiltSpeed){
this.removerTiltMotor.set(tiltSpeed);
}

@Override
  public void stopRemoverMotors()  {
    this.removerMotor.set(0);
  }
  
  @Override
  public void stopTiltMotors()  {
    this.removerTiltMotor.set(0);
  }
  @Override
  public void resetTiltEncoder()  {
    this.removerTiltMotor.setSelectedSensorPosition(0);
  }
  public void updateInputs(AlgaeRemoverInputs inputs){
    inputs.forwardLimitSwitchState = removerTiltMotor.getSensorCollection().isFwdLimitSwitchClosed();
    inputs.backLimitSwitchState = removerTiltMotor.getSensorCollection().isRevLimitSwitchClosed();
    inputs.removerSpeed = removerMotor.get();
    inputs.tiltSpeed = removerTiltMotor.get();
    inputs.tiltMotorEncoderPosition = removerTiltMotor.getSelectedSensorPosition();
  }
}

