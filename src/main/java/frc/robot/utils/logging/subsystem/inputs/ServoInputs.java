package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.providers.InputProvider;
import org.littletonrobotics.junction.LogTable;

public class ServoInputs extends FolderInputs {

  public ServoInputs(String folder) {
    super(folder);
  }

  @Override
  public void toLog(LogTable table) {}

  @Override
  public void fromLog(LogTable table) {}

  @Override
  public boolean process(InputProvider inputProvider) {
    return true;
  }
}
