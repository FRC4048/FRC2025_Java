package frc.robot.utils.logging;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import java.util.List;

public class PathPlannerUtils {
  private static final PathConstraints defualtPathConstraints =
      new PathConstraints(
          Constants.MAX_VELOCITY,
          Constants.MAX_VELOCITY,
          Math.toRadians(1000),
          Math.toRadians(1000));

  public static PathPlannerPath createManualPath(
      Pose2d startPose, Pose2d targetPos, double endVelocity) {
    List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(startPose, targetPos);
    PathPlannerPath path =
        new PathPlannerPath(
            waypoints,
            defualtPathConstraints,
            null,
            new GoalEndState(endVelocity, targetPos.getRotation()));
    path.preventFlipping = true;
    return path;
  }

  public static LoggableCommandWrapper autoFromPath(PathPlannerPath path) {
    return LoggableCommandWrapper.wrap(AutoBuilder.followPath(path));
  }

  public static Command pathToPose(Pose2d targetPos, double endVelocity) {
    return AutoBuilder.pathfindToPose(targetPos, defualtPathConstraints, endVelocity);
  }
}
