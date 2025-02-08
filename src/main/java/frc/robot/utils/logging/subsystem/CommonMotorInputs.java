package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;

public class CommonMotorInputs {
  public static <R> BuildableFolderMotorInputs<R> createLimitedEncoded(
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

  public static <R> BuildableFolderMotorInputs<R> createEncoded(MotorInputBuilder<R> builder) {
    return builder
        .reset()
        .encoderPosition()
        .encoderVelocity()
        .motorCurrent()
        .motorTemperature()
        .build();
  }

  public static <R> BuildableFolderMotorInputs<R> createBasic(MotorInputBuilder<R> builder) {
    return builder.reset().motorCurrent().motorTemperature().build();
  }
}
