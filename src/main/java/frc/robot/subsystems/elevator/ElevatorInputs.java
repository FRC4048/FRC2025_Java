package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ElevatorInputs implements LoggableInputs {
  public double encoder1Position = 0;
  public double encoder1Velocity = 0;
  public double encoder2Position = 0;
  public double encoder2Velocity = 0;
  public double motor1Current = 0;
  public double motor2Current = 0;
  public boolean fwdLimit = false;
  public boolean revLimit = false;

  @Override
  public void toLog(LogTable table) {
    table.put("encoder1Position", encoder1Position);
    table.put("encoder1Velocity", encoder1Velocity);
    table.put("motor1Current", motor1Current);

    table.put("encoder2Position", encoder2Position);
    table.put("encoder2Velocity", encoder2Velocity);
    table.put("motor2Current", motor2Current);

    table.put("fwdLimit", fwdLimit);
    table.put("revLimit", revLimit);
  }

  @Override
  public void fromLog(LogTable table) {
    encoder1Position = table.get("encoder1Position", encoder1Position);
    encoder1Velocity = table.get("encoder1Velocity", encoder1Velocity);
    motor1Current = table.get("motor1Current", motor1Current);

    encoder2Position = table.get("encoder2Position", encoder2Position);
    encoder2Velocity = table.get("encoder2Velocity", encoder2Velocity);
    motor2Current = table.get("motor2Current", motor2Current);

    fwdLimit = table.get("fwdLimit", fwdLimit);
    revLimit = table.get("revLimit", revLimit);
  }
}
