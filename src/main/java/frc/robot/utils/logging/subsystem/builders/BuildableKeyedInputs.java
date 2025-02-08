package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.InputSource;
import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;

/**
 * subclass of {@link KeyedLoggableInputs} that allows keys to be build using an {@link InputSource}
 *
 * @param <R> Hardware class that is used by an {@link InputSource} to pull data from hardware.
 */
public abstract class BuildableKeyedInputs<R> extends KeyedLoggableInputs {

  public BuildableKeyedInputs(String key) {
    super(key);
  }

  abstract void process(R source);
}
