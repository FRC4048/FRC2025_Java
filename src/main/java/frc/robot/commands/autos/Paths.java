package frc.robot.commands.autos;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class Paths {
  private final Command robotOneToPostJCommand;
  private final Command postJToStationOneCommand;
  private final Command stationOneToPostKCommand;
  private final Command postKToStationOneCommand;
  private final Command stationOneToPostLCommand;
  private final Command postLToStationOneCommand;
  private final Command stationOneToPostACommand;

  private static Paths instance;

  public static Paths getInstance() {
    if (instance == null) {
      instance = new Paths();
    }
    return instance;
  }

  public Paths() {
    try {
      robotOneToPostJCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 1 to Post J"));
      postJToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post J to Station 1"));
      stationOneToPostKCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 1 to Post K"));
      postKToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post K to Station 1"));
      stationOneToPostLCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 1 to Post L"));
      postLToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Middle 4 Piece Fork "));
      stationOneToPostACommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Middle One Piece"));

    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public Command getRobotOneToPostJCommand() {
    return robotOneToPostJCommand;
  }

  public Command getPostJToStationOneCommand() {
    return postJToStationOneCommand;
  }

  public Command getStationOneToPostKCommand() {
    return stationOneToPostKCommand;
  }

  public Command getPostKToStationOne() {
    return postKToStationOneCommand;
  }

  public Command getStationOneToPostLCommand() {
    return stationOneToPostLCommand;
  }

  public Command getPostLToStationOneCommand() {
    return postLToStationOneCommand;
  }

  public Command getStationOneToPostACommand() {
    return stationOneToPostACommand;
  }
}
