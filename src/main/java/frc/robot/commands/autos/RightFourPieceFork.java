package frc.robot.commands.autos;

import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import frc.robot.utils.logging.commands.LoggableWaitCommand;

public class RightFourPieceFork extends LoggableSequentialCommandGroup {
  public RightFourPieceFork(
      ElevatorSubsystem elevator, CoralSubsystem coral, LightStrip lightStrip) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getRobotFourToPostECommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevator, lightStrip),
            new LoggableSequentialCommandGroup(
                new LoggableWaitCommand(1.25),
                new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4))),
        new ShootCoral(coral, elevator),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getPostEToStationOneCommand()), // Post E to Station
            new LightlessPickup(elevator, coral)),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(
                Paths.getInstance().getStationTwoToPostCCommand()), // Station 2 to Post B
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevator, lightStrip),
            new LoggableSequentialCommandGroup(
                new LoggableWaitCommand(1.8),
                new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4))),
        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4),
        new ShootCoral(coral, elevator)
        //        new LoggableParallelCommandGroup(
        //            new LoggableCommandWrapper(Paths.getInstance().getPostCToStationTwoCommand())
        //            //            new LightlessPickup(elevator, coral)
        //            ),
        //        new LoggableParallelCommandGroup(
        //            new LoggableCommandWrapper(Paths.getInstance().getStationTwoToPostDCommand())
        //            //            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevator,
        //            // lightStrip)
        //            ),
        //        //        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4),
        //        //        new ShootCoral(coral, Constants.CORAL_SHOOTER_SPEED),
        //        new LoggableParallelCommandGroup(
        //            new LoggableCommandWrapper(
        //                Paths.getInstance().getPostDToStationTwoCommand()) // Post C to Station 2
        //            //            new LightlessPickup(elevator, coral)),
        //            ),
        //        new LoggableParallelCommandGroup(
        //            new LoggableCommandWrapper(
        //                Paths.getInstance().getStationTwoToPostBCommand()) // Station 2 to Post D
        //            new SetElevatorStoredPosition(
        //                ElevatorPosition.LEVEL4, elevator, lightStrip) // Elevator to L4
        );
    //        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4),
    //        new ShootCoral(coral, Constants.CORAL_SHOOTER_SPEED),
    //        new ResetElevator(elevator));
  }
}
