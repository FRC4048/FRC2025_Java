// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.ResetAll;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

/** Add your docs here. */
public class CancelAll extends LoggableSequentialCommandGroup {

  public CancelAll(
      AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem,
      AlgaeByeByeRollerSubsystem algaebyebyeroller,
      ElevatorSubsystem elevatorSubsystem) {
    super(
        new ByeByeAllDone(algaeByeByeTiltSubsystem, algaebyebyeroller, elevatorSubsystem),
        //        new ResetAll(elevatorSubsystem, hihiExtenderSubsystem));
        new ResetAll(elevatorSubsystem));
  }
}
