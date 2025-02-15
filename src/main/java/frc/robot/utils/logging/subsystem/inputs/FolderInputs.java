package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import frc.robot.utils.logging.subsystem.builders.FolderInputBuilder;
import frc.robot.utils.logging.subsystem.processors.InputSource;

/**
 * subclass of {@link FolderLoggableInputs} that allows keys to be build using an {@link
 * InputSource}
 */
public abstract class FolderInputs extends FolderLoggableInputs {

  public FolderInputs(FolderInputBuilder<?> builder) {
    super(builder.getFolder());
  }

  /**
   * @return if processing was successful.
   */
  protected abstract boolean process(InputSource inputSource);
}
