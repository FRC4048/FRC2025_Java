package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.subsystem.builders.BuildableKeyedMotorInputs;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;

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
