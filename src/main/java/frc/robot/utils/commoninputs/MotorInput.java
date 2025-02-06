package frc.robot.utils.commoninputs;

import com.revrobotics.spark.SparkMax;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class MotorInput implements LoggableInputs {
  public double motorCurrent;

  @Override
  public void toLog(LogTable table) {
    table.put("motorCurrent", motorCurrent);
  }

  @Override
  public void fromLog(LogTable table) {
    motorCurrent = table.get("motorCurrent", motorCurrent);
  }

  public void process(SparkMax sparkMax) {
    motorCurrent = sparkMax.getOutputCurrent();
  }
}
