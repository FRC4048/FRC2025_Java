// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.CoralDeposit;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

public class SetElevatorStoredPosition extends Command {
  public final CoralDeposit storedElevatorHeight;
  public final ElevatorSubsystem elevatorSubsystem;

  public SetElevatorStoredPosition(
      CoralDeposit storedElevatorHeight, ElevatorSubsystem elevatorSubsystem) {
    this.storedElevatorHeight = storedElevatorHeight;
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    elevatorSubsystem.setStoredElevatorHeight(storedElevatorHeight);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
