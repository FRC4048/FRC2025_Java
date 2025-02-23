package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.constants.AlignmentPositions;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.auto.PathPlannerUtils;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;

public class AlignClosestBranch extends LoggableCommand {
  private Pose2d targetPosition;
  private final SwerveDrivetrain drivetrain;

  public AlignClosestBranch(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    targetPosition = AlignmentPositions.getClosest(drivetrain.getPose());
    LoggableCommandWrapper.wrap(PathPlannerUtils.pathToPose(targetPosition, 0.0));
  }

  @Override
  public boolean isFinished() {
    return (Math.sqrt(
            Math.pow(drivetrain.getPose().minus(targetPosition).getX(), 2)
                + Math.pow(drivetrain.getPose().minus(targetPosition).getY(), 2))
        < Constants.ALIGNMENT_DISTANCE_THRESHOLD);
  }
}
