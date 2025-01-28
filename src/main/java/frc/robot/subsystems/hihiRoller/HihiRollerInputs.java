package frc.robot.subsystems.hihiRoller;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class HihiRollerInputs implements LoggableInputs {
  public double hihiRollerEncoder = 0;

  @Override
  public void toLog(LogTable table) {
    table.put("hihiRollerEncoder", hihiRollerEncoder);
  }

  @Override
  public void fromLog(LogTable table) {
    hihiRollerEncoder = table.get("hihiRollerEncoder", hihiRollerEncoder);
  }
}
