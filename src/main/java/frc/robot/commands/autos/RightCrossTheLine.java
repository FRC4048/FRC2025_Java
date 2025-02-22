package frc.robot.commands.autos;

import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class RightCrossTheLine extends LoggableSequentialCommandGroup {
  public RightCrossTheLine(ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem) {
    super(
            new LoggableCommandWrapper(
                Paths.getInstance().getRightCrossTheLineCommand()) //RightCrossTheLine
        );
  }
}