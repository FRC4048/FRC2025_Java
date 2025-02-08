package frc.robot.utils.logging.subsystem;

import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;

public class CommonMotorInputs {

  public static <R> void hasEncoder(MotorInputBuilder<R> builder) {
    builder.encoderPosition().encoderVelocity();
  }

  public static <R> void hasStatus(MotorInputBuilder<R> builder) {
    builder.motorCurrent().motorTemperature();
  }

  public static <R> void hasLimits(MotorInputBuilder<R> builder) {
    builder.fwdLimit().revLimit().build();
  }
}
