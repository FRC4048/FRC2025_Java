package frc.robot.commands.sequences;

import frc.robot.commands.climber.DeployHarpoon;
import frc.robot.commands.climber.DisengageRatchet;
import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class DeployHarpoonSequence extends LoggableSequentialCommandGroup {

  public DeployHarpoonSequence(
      ClimberSubsystem climber,
      ElevatorSubsystem elevator,
      LightStrip lightstrip,
      ElevatorPosition safeElevatorPosition) {
    super(
        new DisengageRatchet(climber),
        new SetElevatorStoredPosition(safeElevatorPosition, elevator, lightstrip),
        new ElevatorToStoredPosition(elevator),
        new WaitTillElevatorAtPosition(elevator, safeElevatorPosition.getElevatorHeight()),
        new DeployHarpoon(climber));
  }
}
