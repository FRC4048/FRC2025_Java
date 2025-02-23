// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ResetElevatorEncoder extends LoggableCommand {
  private final ElevatorSubsystem elevator;

  public ResetElevatorEncoder(ElevatorSubsystem elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    elevator.setEncoder(Constants.ELEVATOR_RESET_VALUE);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
