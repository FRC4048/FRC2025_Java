package frc.robot.utils.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;

public class PathPlannerUtils {
  public static final PathConstraints defaultPathConstraints;
  public static final RobotConfig config;

  static {
    RobotConfig tempConf = null;
    PathConstraints tempConstraints = null;
    try {
      tempConf = RobotConfig.fromGUISettings();
      tempConstraints =
          new PathConstraints(
              1.0, //tempConf.moduleConfig.maxDriveVelocityMPS,
              Constants.MAX_PATHPLANNER_ACCEL,
              tempConf.moduleConfig.maxDriveVelocityRadPerSec,
              Constants.MAX_PATHPLANNER_ANGULAR_ACCEL);
    } catch (IOException e) {
      DriverStation.reportError(
          "IO Exception while opening PathPlanner config from GUISettings", true);
    } catch (ParseException e) {
      DriverStation.reportError("Could not parse PathPlanner config from GUISettings", true);
      throw new RuntimeException(e);
    } finally {
      config = tempConf;
      defaultPathConstraints = tempConstraints;
    }
  }

  public static PathPlannerPath createManualPath(
      Pose2d startPose, Pose2d targetPos, double endVelocity) {
    List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(startPose, targetPos);
    PathPlannerPath path =
        new PathPlannerPath(
            waypoints,
            defaultPathConstraints,
            null,
            new GoalEndState(endVelocity, targetPos.getRotation()));
    path.preventFlipping = true;
    return path;
  }

  public static LoggableCommandWrapper autoFromPath(PathPlannerPath path) {
    return LoggableCommandWrapper.wrap(AutoBuilder.followPath(path));
  }

  public static Command pathToPose(Pose2d targetPos, double endVelocity) {
    return AutoBuilder.pathfindToPose(targetPos, defaultPathConstraints, endVelocity);
  }
}
