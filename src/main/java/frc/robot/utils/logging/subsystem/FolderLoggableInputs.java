package frc.robot.utils.logging.subsystem;

import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Subclass of {@link LoggableInputs} that contains a key for logging */
@Deprecated
public abstract class FolderLoggableInputs implements LoggableInputs {
  private final String folder;

  public FolderLoggableInputs(String folder) {
    this.folder = folder;
  }

  public String getFolder() {
    return folder;
  }
}
