package frc.robot.commands.autos;

import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.commands.sequences.PickUpCoral;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class LeftFourPieceFork extends LoggableSequentialCommandGroup {
  public LeftFourPieceFork(
      ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem, LightStrip lightStrip) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getRobotOneToPostJCommand()), // Robot 1 to Post J
            new SetElevatorStoredPosition(
                ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
            ),
        new ShootCoral(
            coralSubsystem,
            0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostJToStationOneCommand()), // Post J To Station 1
            new PickUpCoral(elevatorSubsystem, coralSubsystem, lightStrip), // Reset Elevator
            new LoggableParallelCommandGroup(
                new LoggableCommandWrapper(
                    Paths.getInstance().getStationOneToPostKCommand()), // Station 1 to Post K
                new SetElevatorStoredPosition(
                    ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
                ),
            new ShootCoral(
                coralSubsystem,
                0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
            new LoggableParallelCommandGroup(
                new LoggableCommandWrapper(
                    Paths.getInstance().getPostKToStationOneCommand()), // Post K to Station 1
                new ResetElevator(elevatorSubsystem) // Elevator to L0
                ),
            new IntakeCoral(coralSubsystem), // Intake a Coral
            new LoggableParallelCommandGroup(
                new LoggableCommandWrapper(
                    Paths.getInstance().getStationOneToPostLCommand()), // Station 1 to Post L
                new SetElevatorStoredPosition(
                    ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
                ),
            new ShootCoral(
                coralSubsystem,
                0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
            new LoggableParallelCommandGroup(
                new LoggableCommandWrapper(
                    Paths.getInstance().getPostLToStationOneCommand()), // Post L to Station 1
                new ResetElevator(elevatorSubsystem) // Elevator to L0
                ),
            new IntakeCoral(coralSubsystem), // Intake a Coral
            new LoggableParallelCommandGroup(
                new LoggableCommandWrapper(
                    Paths.getInstance().getStationOneToPostACommand()), // Station One to Post A
                new SetElevatorStoredPosition(
                    ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip) // Elevator to L4
                ),
            new ShootCoral(
                coralSubsystem,
                0.5), // Score a Coral, Updated with the correct Speed according to GameConstants
            new ResetElevator(
                elevatorSubsystem))); // Elevator to L0 we probably won't get to this point but we
    // should
    // move the elevator to L0 at the end of an Auto
  }
}
