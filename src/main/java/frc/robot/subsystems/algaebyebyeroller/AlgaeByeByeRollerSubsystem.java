// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyeroller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;

public class AlgaeByeByeRollerSubsystem extends SubsystemBase {
  /** Creates a new AlgaeRemoverSubsystem. */
  private final LoggableSystem<AlgaeByeByeRollerIO, MotorInputs> algaeSystem;

  public AlgaeByeByeRollerSubsystem(AlgaeByeByeRollerIO io) {
    MotorInputs.Builder<?> builder = new MotorInputs.Builder<>("AlgaeByeByeRollerSubsystem");
    MotorInputs inputs = builder.encoderVelocity().addStatus().build();
    algaeSystem = new LoggableSystem<>(io, inputs);
  }

  @Override
  public void periodic() {
    algaeSystem.updateInputs();
  }

  public void setSpeed(double speed) {
    algaeSystem.getIO().setSpeed(speed);
  }

  public void stopMotors() {
    algaeSystem.getIO().stopMotors();
  }
}
