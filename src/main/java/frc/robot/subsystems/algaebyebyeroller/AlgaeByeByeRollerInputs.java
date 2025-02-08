// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyeroller;

import frc.robot.utils.commoninputs.KeyedInputs;
import org.littletonrobotics.junction.LogTable;

/** Add your docs here. */
public class AlgaeByeByeRollerInputs extends KeyedInputs {
  public double rollerMotorVelocity = 0;

  public AlgaeByeByeRollerInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("rollerMotorVelocity", rollerMotorVelocity);
  }

  @Override
  public void fromLog(LogTable table) {
    rollerMotorVelocity = table.get("rollerMotorVelocity").getDouble();
  }
}
