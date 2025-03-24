// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.sequences;

import frc.robot.commands.climber.ClimbToLimit;
import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

/** Add your docs here. */
public class RetractHarpoon extends LoggableSequentialCommandGroup {

  public RetractHarpoon(
      ClimberSubsystem climber,
      ElevatorSubsystem elevator,
      LightStrip lightstrip,
      ElevatorPosition safeElevatorPosition) {
    super(
        new SetElevatorStoredPosition(safeElevatorPosition, elevator, lightstrip),
        new ElevatorToStoredPosition(elevator),
        new WaitTillElevatorAtPosition(elevator, safeElevatorPosition.getElevatorHeight()),
        new ClimbToLimit(climber, Constants.CLIMBER_PHASE2_SPEED));
  }
}
