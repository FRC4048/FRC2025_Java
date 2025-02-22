package frc.robot.commands.elevator;

import frc.robot.constants.ElevatorPositions;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class ElevatorToPosition extends LoggableSequentialCommandGroup {
  public ElevatorToPosition(
      ElevatorSubsystem elevatorSubsystem, ElevatorPositions elevatorPosition) {
    super(
        new SetElevatorStoredPosition(elevatorPosition, elevatorSubsystem),
        new ElevatorToStoredPosition(elevatorSubsystem));
  }
}
