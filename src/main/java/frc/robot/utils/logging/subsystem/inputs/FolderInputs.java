package frc.robot.utils.logging.subsystem.inputs;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import frc.robot.utils.logging.subsystem.builders.FolderInputBuilder;
import frc.robot.utils.logging.subsystem.providers.InputProvider;

/**
 * subclass of {@link FolderLoggableInputs} that allows keys to be build using an {@link
 * InputProvider}
 */
public abstract class FolderInputs extends FolderLoggableInputs {

  public FolderInputs(FolderInputBuilder<?> builder) {
    super(builder.getFolder());
  }

  /**
   * @return if processing was successful.
   */
  protected abstract boolean process(InputProvider inputProvider);
}
