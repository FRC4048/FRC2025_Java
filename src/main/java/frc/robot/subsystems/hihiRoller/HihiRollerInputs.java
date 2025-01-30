package frc.robot.subsystems.hihiRoller;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class HihiRollerInputs implements LoggableInputs {
    public double hihiRollerEncoder = 0;
    public double hihiRollerVelocity = 0;

    @Override
    public void toLog(LogTable table) {
        table.put("hihiRoller/Encoder", hihiRollerEncoder);
        table.put("hihiRoller/Velocity", hihiRollerVelocity);
    }

    @Override
    public void fromLog(LogTable table) {
        hihiRollerEncoder = table.get("hihiRoller/Encoder", hihiRollerEncoder);
        hihiRollerVelocity = table.get("hihiRoller/Velocity", hihiRollerVelocity);
    }
}
