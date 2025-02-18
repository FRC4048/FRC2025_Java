// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sequences;

import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ElevatorToPosition;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.constants.Constants;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

/** Add your docs here. */
public class ScoreCoral extends LoggableSequentialCommandGroup {

  public ScoreCoral(ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem) {
    super(
        new ElevatorToPosition(elevatorSubsystem),
        new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED),
        new ResetElevator(elevatorSubsystem));
  }
}
