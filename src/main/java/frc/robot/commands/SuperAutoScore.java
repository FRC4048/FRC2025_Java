package frc.robot.commands;

import frc.robot.commands.alignment.AlignClosestBranch;
import frc.robot.commands.alignment.RoughAlignClosestBranch;
import frc.robot.commands.coral.ShootCoral;
import frc.robot.commands.elevator.ElevatorToStoredPosition;
import frc.robot.constants.Constants;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.limelight.Vision;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableParallelCommandGroup;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import frc.robot.utils.logging.commands.LoggableWaitCommand;

public class SuperAutoScore extends LoggableSequentialCommandGroup {
  public SuperAutoScore(
      SwerveDrivetrain drivetrain,
      ElevatorSubsystem elevator,
      CoralSubsystem coralSubsystem,
      Vision vision) {
    super(
        new SetSuperAutoScorePosition(drivetrain, elevator, vision),
        new RoughAlignClosestBranch(drivetrain),
        new LoggableParallelCommandGroup(
            new AlignClosestBranch(drivetrain), new ElevatorToStoredPosition(elevator)),
        new LoggableWaitCommand(0.2),
        new ShootCoral(coralSubsystem, Constants.CORAL_SHOOTER_SPEED));
  }
}
