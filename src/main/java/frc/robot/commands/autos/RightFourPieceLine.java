package frc.robot.commands.autos;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class RightFourPieceLine extends LoggableSequentialCommandGroup {
  public RightFourPieceLine(
      ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem, LightStrip lightStrip) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getRobotFourToPostECommand()), // Robot 4 to Post E
            new SetElevatorStoredPosition(
                ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostEToStationOneCommand()), // Post E to Station One
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationTwoToPostCCommand()), // Station 2 to post C
            new SetElevatorStoredPosition(
                ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostCToStationTwoCommand()), // Post C to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationTwoToPostCCommand()), // Station 2 to post C
            new SetElevatorStoredPosition(
                ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostCToStationTwoCommand()), // Post C to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem), // Intake a Coral
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationTwoToPostCCommand()), // Station 2 to post C
            new SetElevatorStoredPosition(
                ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostCToStationTwoCommand()), // Post C to Station 2
            new ResetElevator(elevatorSubsystem) // Elevator to L0
            ),
        new IntakeCoral(coralSubsystem) // Intake a Coral
        );
  }
}
