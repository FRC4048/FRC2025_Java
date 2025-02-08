package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ElevatorInputs implements LoggableInputs {
  public double elevatorMotorEncoderValue = 0;
  public boolean backLimitSwitchState;
  public boolean forwardLimitSwitchState;

  @Override
  public void toLog(LogTable table) {
    table.put("elevatorMotorEncoderValue", elevatorMotorEncoderValue);
    table.put("backLimitSwitchState", backLimitSwitchState);
    table.put("forwardLimitSwitchState", forwardLimitSwitchState);
  }

  @Override
  public void fromLog(LogTable table) {
    elevatorMotorEncoderValue = table.get("elevatorMotorEncoderValue", elevatorMotorEncoderValue);
    backLimitSwitchState = table.get("backLimitSwitchState", backLimitSwitchState);
    forwardLimitSwitchState = table.get("forwardLimitSwitchState", forwardLimitSwitchState);
  }
}
