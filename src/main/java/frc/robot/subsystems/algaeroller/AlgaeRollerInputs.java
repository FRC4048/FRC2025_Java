package frc.robot.subsystems.algaeroller;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class AlgaeRollerInputs implements LoggableInputs {
  public double algaeRollerEncoder = 0;

  @Override
  public void toLog(LogTable table) {
    table.put("algaeRollerEncoder", algaeRollerEncoder);
  }

  @Override
  public void fromLog(LogTable table) {
    algaeRollerEncoder = table.get("algaeRollerEncoder", algaeRollerEncoder);
  }
}
