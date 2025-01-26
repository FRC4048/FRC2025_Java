package frc.robot.subsystems.algaeroller;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class AlgaeRollerInputs implements LoggableInputs {
  public double algaeRollerEncoder = 0;
  public double algaeAngleEncoder = 0;

  @Override
  public void toLog(LogTable table) {
    table.put("algaeRollerEncoder", algaeRollerEncoder);
    table.put("algaeAngleEncoder", algaeAngleEncoder);
  }

  @Override
  public void fromLog(LogTable table) {
    algaeRollerEncoder = table.get("algaeRollerEncoder", algaeRollerEncoder);
    algaeAngleEncoder = table.get("algaeAngleEncoder", algaeAngleEncoder);
  }
}
