package frc.robot.commands;

import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;

/** Add your docs here. */
public class ElevatorReset extends LoggableParallelCommandGroup {

  public ElevatorReset(ElevatorSubsystem elevatorSubsystem, ReefPosition storedReefPosition) {
    super(
        new SetElevatorStoredPosition(ReefPosition.LEVEL0, elevatorSubsystem),
        new ElevatorToStoredPosition(elevatorSubsystem),
        new SetElevatorStoredPosition(storedReefPosition, elevatorSubsystem));
  }
}
