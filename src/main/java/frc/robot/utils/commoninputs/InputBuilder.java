package frc.robot.utils.commoninputs;

public abstract class InputBuilder<R, S extends BuildableKeyedInputs<R>> {
  protected final String key;

  public InputBuilder(String key) {
    this.key = key;
  }

  public abstract InputBuilder<R, S> reset();

  public abstract S build();
}
