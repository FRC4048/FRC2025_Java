package frc.robot.utils.commoninputs;

public abstract class MotorInputBuilder<R> extends InputBuilder<R, BuildableKeyedMotorInputs<R>> {
  private DoubleInput<R> encoderPosition;
  private DoubleInput<R> encoderVelocity;
  private DoubleInput<R> motorCurrent;
  private DoubleInput<R> motorTemperature;
  private BooleanInput<R> fwdLimit;
  private BooleanInput<R> revLimit;

  public MotorInputBuilder(String key) {
    super(key);
  }

  @Override
  public BuildableKeyedMotorInputs<R> build() {
    return new BuildableKeyedMotorInputs<>(
        key, encoderPosition, encoderVelocity, motorCurrent, motorTemperature, fwdLimit, revLimit);
  }

  public MotorInputBuilder<R> motorCurrent() {
    motorCurrent = new DoubleInput<>("motorCurrent", currentFromSource());
    return this;
  }

  public MotorInputBuilder<R> motorTemperature() {
    motorCurrent = new DoubleInput<>("motorTemperature", motorTemperatureFromSource());
    return this;
  }

  public MotorInputBuilder<R> encoderPosition() {
    encoderPosition = new DoubleInput<>("encoderPosition", encoderPositionFromSource());
    return this;
  }

  public MotorInputBuilder<R> encoderVelocity() {
    encoderVelocity = new DoubleInput<>("encoderVelocity", encoderVelocityFromSource());
    return this;
  }

  public MotorInputBuilder<R> fwdLimit() {
    fwdLimit = new BooleanInput<>("fwdLimit", fwdLimitFromSource());
    return this;
  }

  public MotorInputBuilder<R> revLimit() {
    fwdLimit = new BooleanInput<>("revLimit", revLimitFromSource());
    return this;
  }

  public MotorInputBuilder<R> reset() {
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
