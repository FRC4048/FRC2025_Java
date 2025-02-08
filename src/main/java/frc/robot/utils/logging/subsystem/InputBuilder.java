package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.subsystem.builders.BuildableKeyedInputs;

/**
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware
 * @param <S> subclass of {@link BuildableKeyedInputs} which contains the inputs being logged.
 */
public abstract class InputBuilder<R, S extends BuildableKeyedInputs<R>> {
  protected final String key;

  public InputBuilder(String key) {
    this.key = key;
  }

  public abstract S build();
}
