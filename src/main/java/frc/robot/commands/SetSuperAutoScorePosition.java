package frc.robot.commands;

import frc.robot.constants.AlignmentPosition;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.limelight.Vision;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SetSuperAutoScorePosition extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final ElevatorSubsystem elevatorSubsystem;
  private final Vision vision;

  public SetSuperAutoScorePosition(
      SwerveDrivetrain drivetrain, ElevatorSubsystem elevatorSubsystem, Vision vision) {
    this.drivetrain = drivetrain;
    this.elevatorSubsystem = elevatorSubsystem;
    this.vision = vision;
  }

  @Override
  public void execute() {
    AlignmentPosition closest = AlignmentPosition.getClosest(drivetrain.getPose().getTranslation());
    ElevatorPosition targetPosition =
        GamePieceLocate.calculateBranchLevel(
            closest, vision.getAllBranchPosition(), vision.getAllAlgaePosition());
    if (targetPosition != null) {
      elevatorSubsystem.setElevatorPosition(targetPosition.getElevatorHeight());
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
