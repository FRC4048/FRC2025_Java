package frc.robot.commands.autos;

import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class GoAndWaitAtElevatorPosition extends LoggableSequentialCommandGroup {
  public GoAndWaitAtElevatorPosition(
      ElevatorSubsystem elevatorSubsystem, ElevatorPosition position) {
    super(
        new ElevatorToStoredPosition(elevatorSubsystem),
        new WaitTillElevatorAtPosition(elevatorSubsystem, position.getElevatorHeight()));
  }
}
