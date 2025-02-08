package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.subsystem.builders.BuildableFolderInputs;

/**
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware
 * @param <S> subclass of {@link BuildableFolderInputs} which contains the inputs being logged.
 */
public abstract class InputBuilder<R, S extends BuildableFolderInputs<R>> {
  protected final String folder;

  public InputBuilder(String folder) {
    this.folder = folder;
  }

  public abstract S build();
}
