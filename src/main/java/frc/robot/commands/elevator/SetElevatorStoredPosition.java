// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SetElevatorStoredPosition extends LoggableCommand {
  public final ElevatorPosition elevatorPosition;
  public final ElevatorSubsystem elevatorSubsystem;
  public final LightStrip lightStrip;

  public SetElevatorStoredPosition(
      ElevatorPosition storedElevatorHeight,
      ElevatorSubsystem elevatorSubsystem,
      LightStrip lightStrip) {
    this.elevatorPosition = storedElevatorHeight;
    this.lightStrip = lightStrip;
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem, lightStrip);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (elevatorPosition) {
      case CORAL_INTAKE:
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
        DriverStation.reportError("Invalid Reef Position selected", true);
        break;
    }
    elevatorSubsystem.setStoredReefPosition(elevatorPosition);
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
