package frc.robot.utils.commoninputs;

public class CommonMotorInputs {
  public static <R> BuildableKeyedInputs<R> createLimitedEncoded(InputBuilder<R> builder) {
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

  public static <R> BuildableKeyedInputs<R> createEncoded(InputBuilder<R> builder) {
    return builder
        .reset()
        .encoderPosition()
        .encoderVelocity()
        .motorCurrent()
        .motorTemperature()
        .build();
  }

  public static <R> BuildableKeyedInputs<R> createBasic(InputBuilder<R> builder) {
    return builder.reset().motorCurrent().motorTemperature().build();
  }
}
