// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgaeByeBye;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.constants.Constants;

/** Add your docs here. */
public class RealAlgaeByeByeIO implements AlgaeByeByeIO {
  private final WPI_TalonSRX removerMotor;
  

  public RealAlgaeByeByeIO() {
    this.removerMotor = new WPI_TalonSRX(Constants.ALGAE_REMOVER_SPINING_ID);
    configureMotor();
  }

  private void configureMotor() {
    this.removerMotor.setNeutralMode(NeutralMode.Brake);
    
  }

  @Override
  public void setRemoverSpeed(double speed) {
    this.removerMotor.set(speed);
  }

  @Override
  public void stopRemoverMotors() {
    this.removerMotor.set(0);
  }
  public void updateInputs(AlgaeByeByeInputs inputs) {
   
}
}
