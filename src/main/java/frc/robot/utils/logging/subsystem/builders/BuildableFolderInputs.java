package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import frc.robot.utils.logging.subsystem.processors.InputSource;

/**
 * subclass of {@link FolderLoggableInputs} that allows keys to be build using an {@link
 * InputSource}
 *
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public abstract class BuildableFolderInputs<R> extends FolderLoggableInputs {

  public BuildableFolderInputs(String key) {
    super(key);
  }

  abstract void process(R source);
}
