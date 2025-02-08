package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ElevatorInputs implements LoggableInputs {
  public double elevatorMotorEncoderValue = 0;

  @Override
  public void toLog(LogTable table) {
    table.put("elevatorMotorEncoderValue", elevatorMotorEncoderValue);
  }

  @Override
  public void fromLog(LogTable table) {
    elevatorMotorEncoderValue = table.get("elevatorMotorEncoderValue", elevatorMotorEncoderValue);
  }
}
