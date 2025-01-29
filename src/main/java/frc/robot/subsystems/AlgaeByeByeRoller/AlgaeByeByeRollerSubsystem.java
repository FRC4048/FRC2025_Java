// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgaeByeByeRoller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeByeByeRollerSubsystem extends SubsystemBase {
  /** Creates a new AlgaeRemoverSubsystem. */
  private final LoggableSystem<AlgaeByeByeRollerIO, AlgaeByeByeRollerInputs> algaeSystem;

  public AlgaeByeByeRollerSubsystem(AlgaeByeByeRollerIO io) {
    algaeSystem = new LoggableSystem<>(io, new AlgaeByeByeRollerInputs());
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
