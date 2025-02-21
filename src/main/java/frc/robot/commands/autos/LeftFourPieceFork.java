package frc.robot.commands.autos;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class LeftFourPieceFork extends LoggableSequentialCommandGroup {
   public LeftFourPieceFork(ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem) { 
    super(
      new LoggableParallelCommandGroup(
        Paths.getInstance().getRobotOneToPostJCommand(), //Robot 1 to Post J
        new SetElevatorStoredPosition(ReefPosition.LEVEL4, elevatorSubsystem) //Elevator to L4
      ),
      new ShootCoral(coralSubsystem, 0.5), //Score a Coral
      new LoggableParallelCommandGroup(
        Paths.getInstance().getPostJToStationOneCommand(), //Post J To Station 1
        new ResetElevator(elevatorSubsystem) //Elevator to L0
      ),
      new IntakeCoral(coralSubsystem), //Intake a Coral
      new LoggableParallelCommandGroup(
        Paths.getInstance().getStationOneToPostKCommand(), //Station 1 to Post K
        new SetElevatorStoredPosition(ReefPosition.LEVEL4, elevatorSubsystem) // Elevator to L4
      ),
      new ShootCoral(coralSubsystem, 0.5),  //Score a Coral
      new LoggableParallelCommandGroup(
        Paths.getInstance().getPostKToStationOne(), //Post K to Station 1
        new ResetElevator(elevatorSubsystem) //Elevator to L0
      ),
      new IntakeCoral(coralSubsystem), // Intake a Coral
      new LoggableParallelCommandGroup(
        Paths.getInstance().getStationOneToPostKCommand(), //Station 1 to Post K Command
        new SetElevatorStoredPosition(ReefPosition.LEVEL4, elevatorSubsystem) //Elevator to L4
      ),
      new ShootCoral(coralSubsystem, 0.5), //Score a Coral
      new LoggableParallelCommandGroup(
        Paths.getInstance().getPostKToStationOne(), //Post K to Station 1
        new ResetElevator(elevatorSubsystem) //Elevator to L0
      ),
      );
  }
}
