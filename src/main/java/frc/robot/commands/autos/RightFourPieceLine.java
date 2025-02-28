package frc.robot.commands.autos;

import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.commands.elevator.WaitTillElevatorAtPosition;
import frc.robot.commands.sequences.PickUpCoral;
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
      ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem, LightStrip lightStrip) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getRobotFourToPostECommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip)),
        new ElevatorToStoredPosition(elevatorSubsystem),
        new WaitTillElevatorAtPosition(
            elevatorSubsystem, ElevatorPosition.LEVEL4.getElevatorHeight()),
        new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getPostEToStationOneCommand()),
            new PickUpCoral(elevatorSubsystem, coralSubsystem, lightStrip)),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getStationTwoToPostCCommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevatorSubsystem, lightStrip)),
        new ElevatorToStoredPosition(elevatorSubsystem),
        new WaitTillElevatorAtPosition(
            elevatorSubsystem, ElevatorPosition.LEVEL4.getElevatorHeight()),
        new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getPostCToStationTwoCommand()),
            new PickUpCoral(elevatorSubsystem, coralSubsystem, lightStrip)),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getStationTwoToPostCCommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL3, elevatorSubsystem, lightStrip)),
        new ElevatorToStoredPosition(elevatorSubsystem),
        new WaitTillElevatorAtPosition(
            elevatorSubsystem, ElevatorPosition.LEVEL3.getElevatorHeight()),
        new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getPostCToStationTwoCommand()),
            new PickUpCoral(elevatorSubsystem, coralSubsystem, lightStrip)),
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getStationTwoToPostCCommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL2, elevatorSubsystem, lightStrip)),
        new ElevatorToStoredPosition(elevatorSubsystem),
        new WaitTillElevatorAtPosition(
            elevatorSubsystem, ElevatorPosition.LEVEL2.getElevatorHeight()),
        new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED));
  }
}
