// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.byebye.ByeByeToFwrLimit;
import frc.robot.commands.byebye.SpinByeByeRoller;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;

/** Add your docs here. */
public class RemoveAlgaeFromReef extends LoggableParallelCommandGroup {

  public RemoveAlgaeFromReef(
      AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem,
      AlgaeByeByeRollerSubsystem algaeByeByeRollerSubsystem) {
    super(
        new ByeByeToFwrLimit(algaeByeByeTiltSubsystem),
        new SpinByeByeRoller(algaeByeByeRollerSubsystem));
  }
}
