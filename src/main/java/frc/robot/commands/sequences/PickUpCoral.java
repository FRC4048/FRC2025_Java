// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.commands.lightStrip.SetLedOnCoralIntake;
import frc.robot.commands.lightStrip.SetLedPattern;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class PickUpCoral extends LoggableSequentialCommandGroup {
  public PickUpCoral(ElevatorSubsystem elevator, CoralSubsystem coral, LightStrip lightStrip) {
    super(
        new SetLedPattern(lightStrip, BlinkinPattern.RED),
        new ResetElevator(elevator),
        new WaitTillElevatorAtPosition(elevator, ElevatorPosition.CORAL_INTAKE.getElevatorHeight()),
        new IntakeCoral(coral),
        new SetLedOnCoralIntake(coral::getForwardSwitchState, lightStrip));
  }
}
