package frc.robot.utils.commoninputs;

public abstract class BuildableKeyedInputs<R> extends KeyedInputs {

  public BuildableKeyedInputs(String key) {
    super(key);
  }

  abstract void process(R source);
}
