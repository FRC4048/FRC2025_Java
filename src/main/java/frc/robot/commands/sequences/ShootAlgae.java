// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.hihi.RetractHiHi;
import frc.robot.commands.hihi.ShootHiHiRollerOut;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;

public class ShootAlgae extends LoggableParallelCommandGroup {

  public ShootAlgae(HihiExtenderSubsystem hihiExtender, HihiRollerSubsystem hihiRoller) {
    super(new RetractHiHi(hihiExtender), new ShootHiHiRollerOut(hihiRoller));
  }
}
