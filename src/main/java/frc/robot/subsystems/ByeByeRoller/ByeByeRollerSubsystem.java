// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.ByeByeRoller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class ByeByeRollerSubsystem extends SubsystemBase {
  /** Creates a new AlgaeRemoverSubsystem. */
  private final LoggableSystem<ByeByeRollerIO, ByeByeRollerInputs> algaeSystem;

  public ByeByeRollerSubsystem(ByeByeRollerIO io) {
    algaeSystem = new LoggableSystem<>(io, new ByeByeRollerInputs());
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
