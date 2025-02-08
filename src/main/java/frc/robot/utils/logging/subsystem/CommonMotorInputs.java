package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;

public class CommonMotorInputs {

  public static <R> MotorInputBuilder<R> hasEncoder(MotorInputBuilder<R> builder) {
    return builder.encoderPosition().encoderVelocity();
  }

  public static <R> MotorInputBuilder<R> hasStatus(MotorInputBuilder<R> builder) {
    return builder.motorCurrent().motorTemperature();
  }

  public static <R> BuildableFolderMotorInputs<R> hasLimits(MotorInputBuilder<R> builder) {
    return builder.fwdLimit().revLimit().build();
  }
}
