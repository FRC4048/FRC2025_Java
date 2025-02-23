package frc.robot.commands.autos;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ElevatorPositions;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class RightFourPieceFork extends LoggableSequentialCommandGroup {
  public RightFourPieceFork(
      ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem, LightStrip lightStrip) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getRobotFourToPostECommand()), // Robot 4 to Post E
            new SetElevatorStoredPosition(
                ElevatorPositions.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstant
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostEToStationOneCommand()), // Post E to Station One
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationTwoToPostBCommand()), // Station 2 to Post B
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new ShootCoral(coralSubsystem, 0.5),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostBToStationTwoCommand()), // Post B to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationTwoToPostCCommand()), // Station 2 to Post C
            new SetElevatorStoredPosition(
                ElevatorPositions.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(coralSubsystem, 0.5),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostCToStationTwoCommand()), // Post C to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationTwoToPostDCommand()), // Station 2 to Post D
            new SetElevatorStoredPosition(
                ElevatorPositions.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new ResetElevator(
            elevatorSubsystem) // Elevator to L0 we probably won't get to this point but we should
        // move the elevator to L0 at the end of an Auto
        );
  }
}
