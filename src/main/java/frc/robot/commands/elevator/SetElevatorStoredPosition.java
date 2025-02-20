// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SetElevatorStoredPosition extends LoggableCommand {
  public final ReefPosition reefPosition;
  public final ElevatorSubsystem elevatorSubsystem;
  public final LightStrip lightStrip;

  public SetElevatorStoredPosition(
      ReefPosition storedElevatorHeight,
      ElevatorSubsystem elevatorSubsystem,
      LightStrip lightStrip) {
    this.reefPosition = storedElevatorHeight;
    this.lightStrip = lightStrip;
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (reefPosition == reefPosition.LEVEL0) {
      lightStrip.setPattern(BlinkinPattern.DARK_GREEN);
    } else if (reefPosition == reefPosition.LEVEL1) {
      lightStrip.setPattern(BlinkinPattern.BLUE_VIOLET);
    } else if (reefPosition == reefPosition.LEVEL2) {
      lightStrip.setPattern(BlinkinPattern.DARK_BLUE);
    } else if (reefPosition == reefPosition.LEVEL3) {
      lightStrip.setPattern(BlinkinPattern.ORANGE);
    } else {
      lightStrip.setPattern(BlinkinPattern.RAINBOW_RAINBOW_PALETTE);
    }
    elevatorSubsystem.setStoredReefPosition(reefPosition);
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
    return true;
  }
}
