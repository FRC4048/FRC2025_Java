package frc.robot.utils.commoninputs;

public class CommonMotorInputs {
  public static <R> BuildableKeyedMotorInputs<R> createLimitedEncoded(
      MotorInputBuilder<R> builder) {
    return builder
        .reset()
        .encoderPosition()
        .encoderVelocity()
        .motorCurrent()
        .fwdLimit()
        .revLimit()
        .motorTemperature()
        .build();
  }

  public static <R> BuildableKeyedMotorInputs<R> createEncoded(MotorInputBuilder<R> builder) {
    return builder
        .reset()
        .encoderPosition()
        .encoderVelocity()
        .motorCurrent()
        .motorTemperature()
        .build();
  }

  public static <R> BuildableKeyedMotorInputs<R> createBasic(MotorInputBuilder<R> builder) {
    return builder.reset().motorCurrent().motorTemperature().build();
  }
}
