package frc.robot.subsystems.algaeRoller;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class AlgaeRollerInputs implements LoggableInputs {
  public double algaeAngleSpeed = 0;
  public double algaeRollerSpeed = 0;
  public double algaeRollerEncoder = 0;
  public double algaeAngleEncoder = 0;

  @Override
  public void toLog(LogTable table) {
    table.put("algaeRollerEncoder", algaeRollerEncoder);
    table.put("algaeAngleSpeed", algaeAngleSpeed);
    table.put("algaeRollerSpeed", algaeRollerSpeed);
    table.put("algaeAngleEncoder", algaeAngleEncoder);
  }

  @Override
  public void fromLog(LogTable table) {
    algaeRollerEncoder = table.get("algaeRollerEncoder", algaeRollerEncoder);
    algaeAngleSpeed = table.get("algaeAngleSpeed", algaeAngleSpeed);
    algaeRollerSpeed = table.get("algaeRollerSpeed", algaeRollerSpeed);
    algaeAngleEncoder = table.get("algaeAngleEncoder",algaeAngleEncoder);
  }
}
