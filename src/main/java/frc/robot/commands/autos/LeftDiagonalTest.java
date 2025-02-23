package frc.robot.commands.autos;

import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ElevatorPositions;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class LeftDiagonalTest extends LoggableSequentialCommandGroup {
  public LeftDiagonalTest(ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem) {
    super(
        new LoggableCommandWrapper(Paths.getInstance().getRobotTwoToPostHCommand()),
        new LoggableCommandWrapper(
            new SetElevatorStoredPosition(ElevatorPositions.LEVEL1, elevatorSubsystem)),
        new LoggableCommandWrapper(
            new ShootCoral(coralSubsystem, 0.5)) // Updated with GameConstants
        );
  }
}
