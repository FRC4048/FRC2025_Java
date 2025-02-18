package frc.robot.commands.sequential;

import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import frc.robot.constants.ReefPosition;

public class ShootCoral extends LoggableSequentialCommandGroup {
  public ShootCoral(CoralSubsystem coralSubsystem, ElevatorSubsystem elevatorSubsystem) {
    super(
        new ShootCoral(coralSubsystem, elevatorSubsystem),
        new SetElevatorStoredPosition(ReefPosition.LEVEL0, elevatorSubsystem));
  }
}
