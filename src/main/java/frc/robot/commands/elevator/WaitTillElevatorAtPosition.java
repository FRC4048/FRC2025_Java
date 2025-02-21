// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import frc.robot.constants.Constants;
import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class WaitTillElevatorAtPosition extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final ReefPosition desiredPosition;

  public WaitTillElevatorAtPosition(ElevatorSubsystem elevator, ReefPosition desiredPosition) {
    this.elevator = elevator;
    this.desiredPosition = desiredPosition;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return (((elevator.getElevatorPosition() - desiredPosition.getElevatorHeight())
            < Constants.ELEVATOR_MAX_WINDOW)
        && ((desiredPosition.getElevatorHeight() - elevator.getElevatorPosition())
            < Constants.ELEVATOR_MIN_WINDOW));
  }
}
