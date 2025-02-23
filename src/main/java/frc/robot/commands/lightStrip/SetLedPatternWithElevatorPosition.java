// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.lightStrip;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ElevatorPositions;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;

public class SetLedPatternWithElevatorPosition extends Command {
  private final LightStrip lightStrip;
  private final ElevatorPositions elevatorPosition;
  public SetLedPatternWithElevatorPosition(ElevatorPositions elevatorPosition, LightStrip lightStrip) {
    this.lightStrip = lightStrip;
    this.elevatorPosition = elevatorPosition;
  }

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
