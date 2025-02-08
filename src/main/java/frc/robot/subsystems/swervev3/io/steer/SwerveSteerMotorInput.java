package frc.robot.subsystems.swervev3.io.steer;

import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class SwerveSteerMotorInput extends KeyedLoggableInputs {
  public double steerEncoderPosition = 0;
  public double steerEncoderVelocity = 0;
  public double steerCurrentDraw = 0;

  public SwerveSteerMotorInput(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("steerEncoderPosition", steerEncoderPosition);
    table.put("steerEncoderVelocity", steerEncoderVelocity);
    table.put("steerCurrentDraw", steerCurrentDraw);
  }

  @Override
  public void fromLog(LogTable table) {
    steerEncoderPosition = table.get("steerEncoderPosition", steerEncoderPosition);
    steerEncoderVelocity = table.get("steerEncoderVelocity", steerEncoderVelocity);
    steerCurrentDraw = table.get("steerCurrentDraw", steerCurrentDraw);
  }
}
