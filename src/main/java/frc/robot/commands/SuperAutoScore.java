package frc.robot.commands;

import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.limelight.Vision;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;

public class SuperAutoScore extends LoggableSequentialCommandGroup {
  public SuperAutoScore(
      SwerveDrivetrain drivetrain,
      ElevatorSubsystem elevator,
      CoralSubsystem coralSubsystem,
      Vision vision) {
    super(new SetSuperAutoScorePosition(drivetrain, elevator, vision));
  }
}
