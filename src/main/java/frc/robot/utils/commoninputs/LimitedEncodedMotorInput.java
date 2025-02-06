package frc.robot.utils.commoninputs;

import com.revrobotics.spark.SparkMax;
import org.littletonrobotics.junction.LogTable;

public class LimitedEncodedMotorInput extends EncodedMotorInput {
  public boolean fwdLimit;
  public boolean revLimit;

  @Override
  public void toLog(LogTable table) {
    super.toLog(table);
    table.put("fwdLimit", fwdLimit);
    table.put("revLimit", revLimit);
  }

  @Override
  public void fromLog(LogTable table) {
    super.fromLog(table);
    fwdLimit = table.get("fwdLimit", fwdLimit);
    revLimit = table.get("revLimit", revLimit);
  }

  @Override
  public void process(SparkMax sparkMax) {
    super.process(sparkMax);
    fwdLimit = sparkMax.getForwardLimitSwitch().isPressed();
    revLimit = sparkMax.getReverseLimitSwitch().isPressed();
  }
}
