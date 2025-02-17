package frc.robot.commands.autos;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class Paths {
  private final Command robot1ToPostICommand;
  private final Command poseItoStation1Command;
  private final Command station1ToPoseACommand;

  private static Paths instance;

  public static Paths getInstance() {
    if (instance == null) {
      instance = new Paths();
    }
    return instance;
  }

  public Paths() {
    try {
      PathPlannerPath robot1ToPostIPath = PathPlannerPath.fromPathFile("Robot 1 to Post I");
      PathPlannerPath poseItoStation1Path = PathPlannerPath.fromPathFile("Post I to Station 1");
      PathPlannerPath station1ToPoseAPath = PathPlannerPath.fromPathFile("Station 1 to Post A");
      robot1ToPostICommand = AutoBuilder.followPath(robot1ToPostIPath);
      poseItoStation1Command = AutoBuilder.followPath(poseItoStation1Path);
      station1ToPoseACommand = AutoBuilder.followPath(station1ToPoseAPath);
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public Command getRobot1ToPostICommand() {
    return robot1ToPostICommand;
  }

  public Command getPoseItoStation1Command() {
    return poseItoStation1Command;
  }

  public Command getStation1ToPoseACommand() {
    return station1ToPoseACommand;
  }
}
