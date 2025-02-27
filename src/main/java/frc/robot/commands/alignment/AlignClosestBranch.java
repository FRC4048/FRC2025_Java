package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.constants.AlignmentPositions;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.auto.PathPlannerUtils;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.math.PoseUtils;

public class AlignClosestBranch extends LoggableCommand {
  private Pose2d targetPosition;
  private final SwerveDrivetrain drivetrain;
  private int counter = 0;

  public AlignClosestBranch(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    counter = 0;
    targetPosition = AlignmentPositions.getClosest(drivetrain.getPose());
    LoggableCommandWrapper.wrap(PathPlannerUtils.pathToPose(targetPosition, 0.0)).schedule();
  }

  @Override
  public void execute() {
    if (PoseUtils.getDistance(drivetrain.getPose(), targetPosition)
            < Constants.ALIGNMENT_DISTANCE_THRESHOLD) {
      counter++;
    } else {
      counter = 0;
    }
  }

  @Override
  public boolean isFinished() {
    return (counter > 20);
  }
}
