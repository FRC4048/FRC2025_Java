// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyeroller;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Add your docs here. */
public class AlgaeByeByeRollerInputs implements LoggableInputs {
  public double rollerMotorVelocity = 0;

  @Override
  public void toLog(LogTable table) {
    table.put("rollerMotorVelocity", rollerMotorVelocity);
  }

  @Override
  public void fromLog(LogTable table) {
    rollerMotorVelocity = table.get("rollerMotorVelocity").getDouble();
  }
}
