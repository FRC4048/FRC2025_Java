package frc.robot.commands.autos;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class LeftFourPieceFork extends LoggableSequentialCommandGroup {
   public LeftFourPieceFork(ElevatorSubsystem elevatorSubsystem) {
    Paths.getInstance().getRobotOneToPostJCommand();
      super(
        new ParallelCommandGroup(
          LoggableCommandWrapper.wrap(AutoBuilder.followPath(Robot1ToPostJ));
          new SetElevatorStoredPosition(ReefPosition.LEVEL4, elevatorSubsystem))
        

      )
  }
}
