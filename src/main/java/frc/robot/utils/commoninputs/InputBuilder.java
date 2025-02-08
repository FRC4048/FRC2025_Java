package frc.robot.utils.commoninputs;

public abstract class InputBuilder<R> {
  private final String key;
  private DoubleInput<R> encoderPosition;
  private DoubleInput<R> encoderVelocity;
  private DoubleInput<R> motorCurrent;
  private DoubleInput<R> motorTemperature;
  private BooleanInput<R> fwdLimit;
  private BooleanInput<R> revLimit;

  public InputBuilder(String key) {
    this.key = key;
  }

  public BuildableKeyedInputs<R> build() {
    return new BuildableKeyedInputs<>(
        key, encoderPosition, encoderVelocity, motorCurrent, motorTemperature, fwdLimit, revLimit);
  }

  public InputBuilder<R> motorCurrent() {
    motorCurrent = new DoubleInput<>("motorCurrent", currentFromSource());
    return this;
  }

  public InputBuilder<R> motorTemperature() {
    motorCurrent = new DoubleInput<>("motorTemperature", motorTemperatureFromSource());
    return this;
  }

  public InputBuilder<R> encoderPosition() {
    encoderPosition = new DoubleInput<>("encoderPosition", encoderPositionFromSource());
    return this;
  }

  public InputBuilder<R> encoderVelocity() {
    encoderVelocity = new DoubleInput<>("encoderVelocity", encoderVelocityFromSource());
    return this;
  }

  public InputBuilder<R> fwdLimit() {
    fwdLimit = new BooleanInput<>("fwdLimit", fwdLimitFromSource());
    return this;
  }

  public InputBuilder<R> revLimit() {
    fwdLimit = new BooleanInput<>("revLimit", revLimitFromSource());
    return this;
  }

  public InputBuilder<R> reset() {
    encoderPosition = null;
    encoderVelocity = null;
    motorCurrent = null;
    motorTemperature = null;
    fwdLimit = null;
    revLimit = null;
    return this;
  }

  protected abstract InputSource<Double, R> currentFromSource();

  protected abstract InputSource<Double, R> motorTemperatureFromSource();

  protected abstract InputSource<Double, R> encoderPositionFromSource();

  protected abstract InputSource<Double, R> encoderVelocityFromSource();

  protected abstract InputSource<Boolean, R> fwdLimitFromSource();

  protected abstract InputSource<Boolean, R> revLimitFromSource();
}
