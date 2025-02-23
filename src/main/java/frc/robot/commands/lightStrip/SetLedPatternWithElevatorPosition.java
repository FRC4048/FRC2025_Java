// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.lightStrip;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;

public class SetLedPatternWithElevatorPosition extends Command {
  private final LightStrip lightStrip;
  private final ElevatorSubsystem elevatorSubsystem;

  public SetLedPatternWithElevatorPosition(
      ElevatorSubsystem elevatorSubsystem, LightStrip lightStrip) {
    this.lightStrip = lightStrip;
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(lightStrip);
  }

  @Override
  public void initialize() {
    switch (elevatorSubsystem.getStoredReefPosition()) {
      case LEVEL1:
        lightStrip.setPattern(BlinkinPattern.WHITE);
        break;
      case LEVEL2:
        lightStrip.setPattern(BlinkinPattern.YELLOW);
        break;
      case LEVEL3:
        lightStrip.setPattern(BlinkinPattern.BLUE);
        break;
      case LEVEL4:
        lightStrip.setPattern(BlinkinPattern.RAINBOW_RAINBOW_PALETTE);
        break;
      default:
        DriverStation.reportError("Invalid Reef Position selected", true);
        break;
    }
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
