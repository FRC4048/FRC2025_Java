// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intakeassist;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class IntakeAssistSubsystem extends SubsystemBase {
  /** Creates a new IntakeAssistSubsystem. */
  private final LoggableSystem<IntakeAssistIO, MotorInputs> intakeAssistSystem;

  public IntakeAssistSubsystem(IntakeAssistIO io) {
    MotorInputs inputs = new MotorInputBuilder<>("IntakeAssistSubsystem").addAll().build();
    intakeAssistSystem = new LoggableSystem<>(io, inputs);
  }

  @Override
  public void periodic() {
    intakeAssistSystem.updateInputs();
  }

  public void resetIntakeAssistEncoder() {
    intakeAssistSystem.getIO().resetIntakeAssistEncoder();
  }

  public void setIntakeAssistSpeed(double speed) {
    intakeAssistSystem.getIO().setSpeed(speed);
  }

  public void stopIntakeAssistMotors() {
    intakeAssistSystem.getIO().stopMotors();
  }
}
