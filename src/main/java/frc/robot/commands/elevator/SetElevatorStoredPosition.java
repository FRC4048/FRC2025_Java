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
    addRequirements(lightStrip);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (reefPosition) {
      case LEVEL0:
        lightStrip.setPattern(BlinkinPattern.DARK_GREEN);
        break;
      case LEVEL1:
        lightStrip.setPattern(BlinkinPattern.BLUE_VIOLET);
        break;
      case LEVEL2:
        lightStrip.setPattern(BlinkinPattern.DARK_BLUE);
        break;
      case LEVEL3:
        lightStrip.setPattern(BlinkinPattern.ORANGE);
        break;
      case LEVEL4:
        lightStrip.setPattern(BlinkinPattern.RAINBOW_RAINBOW_PALETTE);
        break;
      default:
        break;
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
