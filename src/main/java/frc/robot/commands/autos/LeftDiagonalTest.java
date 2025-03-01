package frc.robot.commands.autos;

import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class LeftDiagonalTest extends LoggableSequentialCommandGroup {
  public LeftDiagonalTest(
      ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem, LightStrip lightStrip) {
    super(
        new LoggableCommandWrapper(Paths.getInstance().getRobotTwoToPostHCommand()),
        new LoggableCommandWrapper(
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL1, elevatorSubsystem, lightStrip)),
        new LoggableCommandWrapper(
            new ShootCoral(
                coralSubsystem, Constants.CORAL_SHOOTER_SPEED)) // Updated with GameConstants
        );
  }
}
