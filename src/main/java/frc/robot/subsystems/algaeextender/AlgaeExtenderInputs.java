// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaeextender;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Add your docs here. */
public class AlgaeExtenderInputs implements LoggableInputs {
  public double algaeExtenderEncoderPos = 0;
  public boolean revTripped = false;
  public boolean fwdTripped = false;

  @Override
  public void toLog(LogTable table) {
    table.put("algaeExtenderEncoderPos", algaeExtenderEncoderPos);
    table.put("lowerTripped", revTripped);
    table.put("upperTripped", fwdTripped);
  }

  @Override
  public void fromLog(LogTable table) {
    algaeExtenderEncoderPos = table.get("algaeExtenderEncoderPos", algaeExtenderEncoderPos);
    revTripped = table.get("lowerTripped", revTripped);
    fwdTripped = table.get("upperTripped", fwdTripped);
  }
}
