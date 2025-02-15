package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.FolderInputs;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import org.littletonrobotics.junction.LogTable;

public abstract class FolderInputBuilder<T extends FolderInputBuilder<T>> {

  private final String folder;

  public FolderInputBuilder(String folder) {
    this.folder = folder;
  }

  T self() {
    return (T) this;
  }

  public abstract T reset();

  public FolderInputs build() {
    return new FolderInputs(this) {
      @Override
      protected boolean process(InputProvider inputProvider) {
        return true;
      }

      @Override
      public void toLog(LogTable table) {}

      @Override
      public void fromLog(LogTable table) {}
    };
  }

  public String getFolder() {
    return folder;
  }
}
