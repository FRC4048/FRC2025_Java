// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class WaitTillElevatorAtPosition extends LoggableCommand {
  private double position;
  private final ElevatorSubsystem elevatorSubsystem;
  private double counter = 0;

  public WaitTillElevatorAtPosition(ElevatorSubsystem elevatorSubsystem, double position) {
    this.elevatorSubsystem = elevatorSubsystem;
    this.position = position;
    addRequirements(elevatorSubsystem);
  }

  @Override
  public void initialize() {
    counter = 0;
  }

  @Override
  public void execute() {
    if (Math.abs(elevatorSubsystem.getElevatorTargetPosition() - position) < 1) {
      counter++;
    }
  }

  @Override
  public boolean isFinished() {
    return counter > 10;
  }
}
