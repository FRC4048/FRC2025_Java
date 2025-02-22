package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.byebye.ByeByeToFwrLimit;
import frc.robot.commands.coral.IntakeCoral;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.elevator.SetElevatorStoredPosition;
import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class MiddleOnePiece extends LoggableSequentialCommandGroup {
  public MiddleOnePiece(ElevatorSubsystem elevatorSubsystem, CoralSubsystem coralSubsystem, AlgaeByeByeTiltSubsystem algaeByeByeTiltSubsystem) {
    super(
            new LoggableCommandWrapper(
                Paths.getInstance().getRobotTwoToPostHCommand()),
                new WaitCommand(11.79),
                new ByeByeToFwrLimit(algaeByeByeTiltSubsystem, 0.5)
        );
   
  }
}
