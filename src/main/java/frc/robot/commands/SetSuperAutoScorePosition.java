package frc.robot.commands;

import frc.robot.constants.AlignmentPosition;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.limelight.Vision;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.logging.commands.LoggableCommand;
import org.littletonrobotics.junction.Logger;

public class SetSuperAutoScorePosition extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final ElevatorSubsystem elevatorSubsystem;
  private final Vision vision;

  public SetSuperAutoScorePosition(
      SwerveDrivetrain drivetrain, ElevatorSubsystem elevatorSubsystem, Vision vision) {
    this.drivetrain = drivetrain;
    this.elevatorSubsystem = elevatorSubsystem;
    this.vision = vision;
    addRequirements(drivetrain, elevatorSubsystem, vision);
  }

  @Override
  public void execute() {
    AlignmentPosition closest = AlignmentPosition.getClosest(drivetrain.getPose().getTranslation());
    Logger.recordOutput("closest", closest);
    ElevatorPosition targetPosition =
        GamePieceLocate.calculateBranchLevel(
            closest, vision.getAllBranchPosition(), vision.getAllAlgaePosition());
    Logger.recordOutput("SuperAlignTargetPosition", targetPosition);
    if (targetPosition != null) {
      elevatorSubsystem.setStoredReefPosition(targetPosition);
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
