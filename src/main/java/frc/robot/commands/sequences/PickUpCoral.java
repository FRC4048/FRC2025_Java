// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.commands.lightStrip.SetLedOnCoralIntake;
import frc.robot.commands.lightStrip.SetLedPatternForever;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableDeadlineCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class PickUpCoral extends LoggableSequentialCommandGroup {
  public PickUpCoral(
      ElevatorSubsystem elevator,
      AlgaeByeByeTiltSubsystem byeByeTilt,
      AlgaeByeByeRollerSubsystem byeByeRoller,
      CoralSubsystem coral,
      LightStrip lightStrip) {
    super(
        new LoggableDeadlineCommandGroup(
            new LoggableSequentialCommandGroup(
                new LowerElevator(byeByeTilt, byeByeRoller, elevator),
                new WaitTillElevatorAtPosition(
                    elevator, ElevatorPosition.CORAL_INTAKE.getElevatorHeight()),
                new IntakeCoral(coral)),
            new SetLedPatternForever(lightStrip, BlinkinPattern.RED)),
        new SetLedOnCoralIntake(coral::getForwardSwitchState, lightStrip));
  }
}
