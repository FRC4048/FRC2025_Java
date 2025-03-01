package frc.robot.commands.autos;

import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class RightFourPieceLine extends LoggableSequentialCommandGroup {
  public RightFourPieceLine(
      ElevatorSubsystem elevator, CoralSubsystem coral, LightStrip lightStrip) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getRobotFourToPostECommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevator, lightStrip)),
        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4),
        new ShootCoral(coral, Constants.CORAL_SHOOTER_SPEED),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getPostEToStationOneCommand()),
            new LightlessPickup(elevator, coral)),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getStationTwoToPostCCommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevator, lightStrip)),
        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4),
        new ShootCoral(coral, Constants.CORAL_SHOOTER_SPEED),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getPostCToStationTwoCommand()),
            new LightlessPickup(elevator, coral)),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getStationTwoToPostCCommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL3, elevator, lightStrip)),
        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL3),
        new ShootCoral(coral, Constants.CORAL_SHOOTER_SPEED),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getPostCToStationTwoCommand()),
            new LightlessPickup(elevator, coral)),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getStationTwoToPostCCommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL2, elevator, lightStrip)),
        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL2),
        new ShootCoral(coral, Constants.CORAL_SHOOTER_SPEED),
        new ResetElevator(elevator));
  }
}
