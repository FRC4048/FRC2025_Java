// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.byebye.ByeByeToFwrLimit;
import frc.robot.commands.byebye.StopByeByeMotors;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

/** Add your docs here. */
public class ByeByeAllDone extends LoggableSequentialCommandGroup {

  public ByeByeAllDone(
      AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem,
      AlgaeByeByeRollerSubsystem algaeByeByeRollerSubsystem,
      ElevatorSubsystem elevatorSubsystem) {
    super(
        new StopByeByeMotors(algaeByeByeRollerSubsystem),
        new ByeByeToFwrLimit(algaeByeByeTiltSubsystem));
  }
}
