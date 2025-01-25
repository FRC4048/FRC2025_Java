// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgaeRemover;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Add your docs here. */
public class AlgaeRemoverInputs implements LoggableInputs{
  public double tiltMotorEncoderPosition = 0;
  public boolean backLimitSwitchState;
  public boolean forwardLimitSwitchState;

  public void toLog(LogTable table){
    table.put("tiltMotorEncoderPosition", tiltMotorEncoderPosition);
    table.put("backLimitSwitchState", backLimitSwitchState);
    table.put("forwardLimitSwitchState", forwardLimitSwitchState);
}
public void fromLog(LogTable table){
    tiltMotorEncoderPosition = table.get("tiltMotorEncoderPosition", tiltMotorEncoderPosition);
    backLimitSwitchState = table.get("backLimitSwitchState", backLimitSwitchState);
    forwardLimitSwitchState = table.get("forwardLimitSwitchState", forwardLimitSwitchState);
}


}