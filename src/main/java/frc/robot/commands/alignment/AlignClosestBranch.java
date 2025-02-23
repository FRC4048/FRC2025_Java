package frc.robot.commands.alignment;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.constants.AlignmentPositions;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import java.util.List;

public class AlignClosestBranch extends LoggableCommand {
  private Pose2d targetPosition;
  private final SwerveDrivetrain drivetrain;

  public AlignClosestBranch(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    targetPosition = AlignmentPositions.getClosest(drivetrain.getPose());
    List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(targetPosition);
    PathConstraints constraints =
        new PathConstraints(3.0, 3.0, 2 * Math.PI, 4 * Math.PI); // The constraints for this path.
    PathPlannerPath path =
        new PathPlannerPath(
            waypoints, constraints, null, new GoalEndState(0.0, targetPosition.getRotation()));
    LoggableCommandWrapper.wrap(AutoBuilder.followPath(path));
  }

  @Override
  public boolean isFinished() {
    return (Math.sqrt(
            Math.pow(drivetrain.getPose().minus(targetPosition).getX(), 2)
                + Math.pow(drivetrain.getPose().minus(targetPosition).getY(), 2))
        < Constants.ALIGNMENT_DISTANCE_THRESHOLD);
  }
}
