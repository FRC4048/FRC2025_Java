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

public class MiddleOnePiece extends LoggableSequentialCommandGroup {
  public MiddleOnePiece(ElevatorSubsystem elevator, CoralSubsystem coral, LightStrip lightStrip) {
    super(
        new LoggableParallelCommandGroup(
            new LoggableCommandWrapper(Paths.getInstance().getRobotTwoToPostHCommand()),
            new SetElevatorStoredPosition(ElevatorPosition.LEVEL4, elevator, lightStrip)),
        new GoAndWaitAtElevatorPosition(elevator, ElevatorPosition.LEVEL4),
        new ShootCoral(coral, elevator::getStoredReefPosition));
  }
}
