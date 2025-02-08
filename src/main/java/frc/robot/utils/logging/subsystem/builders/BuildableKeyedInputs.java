package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.KeyedLoggableInputs;

public abstract class BuildableKeyedInputs<R> extends KeyedLoggableInputs {

  public BuildableKeyedInputs(String key) {
    super(key);
  }

  abstract void process(R source);
}
