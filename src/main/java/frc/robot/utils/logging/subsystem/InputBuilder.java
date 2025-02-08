package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.subsystem.builders.BuildableKeyedInputs;

public abstract class InputBuilder<R, S extends BuildableKeyedInputs<R>> {
  protected final String key;

  public InputBuilder(String key) {
    this.key = key;
  }

  public abstract S build();
}
