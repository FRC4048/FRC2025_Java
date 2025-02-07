package frc.robot.utils.commoninputs;

import com.revrobotics.spark.SparkMax;
import org.littletonrobotics.junction.LogTable;

public class EncodedMotorInput extends MotorInput {
  private double encoderPosition;
  private double encoderVelocity;

  @Override
  public void toLog(LogTable table) {
    super.toLog(table);
    table.put("encoderPosition", encoderPosition);
    table.put("encoderVelocity", encoderVelocity);
  }

  @Override
  public void fromLog(LogTable table) {
    super.fromLog(table);
    encoderPosition = table.get("encoderPosition", encoderPosition);
    encoderVelocity = table.get("encoderVelocity", encoderVelocity);
  }

  @Override
  public void process(SparkMax sparkMax) {
    super.process(sparkMax);
    encoderPosition = sparkMax.getEncoder().getPosition();
    encoderVelocity = sparkMax.getEncoder().getVelocity();
  }

  public double getEncoderPosition() {
    return encoderPosition;
  }

  public double getEncoderVelocity() {
    return encoderVelocity;
  }
}
