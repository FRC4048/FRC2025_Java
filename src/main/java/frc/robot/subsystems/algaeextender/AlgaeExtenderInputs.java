// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaeextender;
import  org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;
import frc.robot.constants.Constants;

/** Add your docs here. */
public class AlgaeExtenderInputs implements LoggableInputs{
    public double algaeExtenderEncoderPos = 0;
    public double algaeSpeed = Constants.EXTENDER_MOTOR_FORWARD_SPEED;
    public boolean lowerTripped = false;
    public boolean upperTripped = false;
    @Override
    public void toLog(logTable table) {
        table.put("algaeExtenderEncoderPos", algaeExtenderEncoderPos);
        table.put("algaeSpeed", algaeSpeed);
        table.put("lowerTripped", lowerTripped);
        table.put("upperTripped", upperTripped);
    }
    @Override
    public void fromLog(LogTable table) {
        algaeExtenderEncoderPos = table.get("algaeExtenderEncoderPos", algaeExtenderEncoderPos);
        algaeSpeed = table.get("algaeSpeed", algaeSpeed);
        lowerTripped = table.get("lowerTripped", lowerTripped);
        upperTripped = table.get("upperTripped", upperTripped);
    }
}
