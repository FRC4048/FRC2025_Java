package frc.robot.utils.commoninputs;

import com.revrobotics.spark.SparkMax;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class MotorInput implements LoggableInputs {
  private double motorCurrent;
  private double motorTemperature;

  @Override
  public void toLog(LogTable table) {
    table.put("motorCurrent", motorCurrent);
    table.put("motorTemperature", motorTemperature);
  }

  @Override
  public void fromLog(LogTable table) {
    motorCurrent = table.get("motorCurrent", motorCurrent);
    motorTemperature = table.get("motorTemperature", motorTemperature);
  }

  public void process(SparkMax sparkMax) {
    motorCurrent = sparkMax.getOutputCurrent();
    motorTemperature = sparkMax.getMotorTemperature();
  }

  public double getMotorCurrent() {
    return motorCurrent;
  }

  public double getMotorTemperature() {
    return motorTemperature;
  }
}
