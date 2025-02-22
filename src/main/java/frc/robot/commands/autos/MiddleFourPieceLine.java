package frc.robot.commands.autos;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ElevatorPositions;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class MiddleFourPieceLine extends LoggableSequentialCommandGroup {
  public MiddleFourPieceLine(ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getRobotTwoToPostHCommand()), // Robot 4 to Post E
            new SetElevatorStoredPosition(
                ElevatorPositions.LEVEL4, elevatorSubsystem) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostHToStationeOneCommand()), // Post E to Station One
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationOneToPostKCommand()), // Station 2 to post K
            new SetElevatorStoredPosition(
                ElevatorPositions.LEVEL4, elevatorSubsystem) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostKToStationOneCommand()), // Post K to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationOneToPostKCommand()), // Station 2 to post K
            new SetElevatorStoredPosition(
                ElevatorPositions.LEVEL4, elevatorSubsystem) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostKToStationOneCommand()), // Post K to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationOneToPostKCommand()), // Station 2 to post K
            new SetElevatorStoredPosition(
                ElevatorPositions.LEVEL4, elevatorSubsystem) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostKToStationOneCommand()), // Post K to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem) // Intake a Coral
        );
  }
}
