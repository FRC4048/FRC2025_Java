package frc.robot.commands.sequential;

import frc.robot.commands.elevator.SetElevatorTargetPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class ShootCoral extends LoggableSequentialCommandGroup {
  public ShootCoral(CoralSubsystem coralSubsystem, ElevatorSubsystem elevatorSubsystem) {
    super(
        new ShootCoral(coralSubsystem, elevatorSubsystem),
        new SetElevatorTargetPosition(null, elevatorSubsystem));
  }
}
