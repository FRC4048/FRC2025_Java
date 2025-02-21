package frc.robot.commands.elevator;

import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class SetElevatorStoredPositionAndGo extends LoggableSequentialCommandGroup {
  public SetElevatorStoredPositionAndGo(
      ElevatorSubsystem elevatorSubsystem, ReefPosition reefPosition) {
    addCommands(
        new SetElevatorStoredPosition(reefPosition, elevatorSubsystem),
        new ElevatorToStoredPosition(elevatorSubsystem));
  }
}
