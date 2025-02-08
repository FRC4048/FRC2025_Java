package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.KeyedInputs;

public abstract class BuildableKeyedInputs<R> extends KeyedInputs {

  public BuildableKeyedInputs(String key) {
    super(key);
  }

  abstract void process(R source);
}
