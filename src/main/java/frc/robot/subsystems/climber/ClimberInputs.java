// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Add your docs here. */
public class ClimberInputs implements LoggableInputs{
  public double speedMotor;

  @Override
public void toLog(LogTable table){
table.put("speedMotor", speedMotor);
}

  @Override
  public void fromLog(LogTable table){
    speedMotor = table.get("speedMotor", speedMotor);

  }
}
