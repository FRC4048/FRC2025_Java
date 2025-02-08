package frc.robot.subsystems.swervev3.io.abs;

import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class SwerveAbsInput extends KeyedLoggableInputs {

  public double absEncoderPosition;

  public SwerveAbsInput(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("absEncoderPosition", absEncoderPosition);
  }

  @Override
  public void fromLog(LogTable table) {
    absEncoderPosition = table.get("absEncoderPosition", absEncoderPosition);
  }
}
