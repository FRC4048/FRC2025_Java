package frc.robot.commands.sequences;

import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.Constants;
import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class ScoreCoral extends LoggableSequentialCommandGroup {
  public ScoreCoral(
      CoralSubsystem coralSubsystem,
      ElevatorSubsystem elevatorSubsystem,
      ReefPosition reefPosition) {
    super(
        new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED),
        new SetElevatorStoredPosition(reefPosition, elevatorSubsystem));
  }
}
