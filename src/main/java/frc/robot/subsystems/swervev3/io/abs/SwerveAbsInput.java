package frc.robot.subsystems.swervev3.io.abs;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class SwerveAbsInput extends FolderLoggableInputs {

  public double absEncoderPosition;

  public SwerveAbsInput(String folder) {
    super(folder);
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
