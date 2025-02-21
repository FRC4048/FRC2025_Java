// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class PickUpCoral extends LoggableSequentialCommandGroup {
  public PickUpCoral(ElevatorSubsystem elevator, CoralSubsystem coral) {
    super(
        new ResetElevator(elevator),
        new WaitTillElevatorAtPosition(elevator, 0),
        new IntakeCoral(coral));
  }
}
