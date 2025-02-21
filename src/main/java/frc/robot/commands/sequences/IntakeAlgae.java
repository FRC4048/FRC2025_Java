// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.hihi.ExtendHiHi;
import frc.robot.commands.hihi.IntakeTillAlgae;
import frc.robot.commands.hihi.RetractHiHi;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class IntakeAlgae extends LoggableSequentialCommandGroup {

  public IntakeAlgae(HihiExtenderSubsystem hihiExtender, HihiRollerSubsystem hihiRoller) {
    super(
      new ExtendHiHi(hihiExtender),
      new IntakeTillAlgae(hihiRoller),
      new RetractHiHi(hihiExtender)
    );
  }
}
