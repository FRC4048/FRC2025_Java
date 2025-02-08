// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Add your docs here. */
public class ClimberInputs implements LoggableInputs {
  public double climberMotorEncoder;
  public boolean climberReversedLimitSwitchPressed;
  public boolean climberForwardLimitSwitchPressed;

  @Override
  public void toLog(LogTable table) {
    table.put("climberMotorEncoder", climberMotorEncoder);
    table.put("climberReversedLimitSwitchPressed", climberReversedLimitSwitchPressed);
    table.put("climberForwardLimitSwitchPressed", climberForwardLimitSwitchPressed);
  }

  @Override
  public void fromLog(LogTable table) {
    climberMotorEncoder = table.get("climberMotorEncoder", climberMotorEncoder);
    climberReversedLimitSwitchPressed =
        table.get("climberReversedLimitSwitchPressed", climberReversedLimitSwitchPressed);
    climberForwardLimitSwitchPressed =
        table.get("climberForwardLimitSwitchPressed", climberForwardLimitSwitchPressed);
  }
}
