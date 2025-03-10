package frc.robot.commands.autos;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class Paths {
  // Robot to Post
  private final Command robotOneToPostJCommand;
  private final Command robotFourToPostECommand;
  private final Command robotTwoToPostHCommand;
  // Post to Station
  private final Command postJToStationOneCommand;
  private final Command postKToStationOneCommand;
  private final Command postLToStationOneCommand;
  private final Command postHToStationOneCommand;
  private final Command postEToStationOneCommand;
  private final Command postBToStationTwoCommand;
  private final Command postCToStationTwoCommand;
  private final Command postDToStationTwoCommand;
  // Station to Post
  private final Command stationOneToPostKCommand;
  private final Command stationOneToPostLCommand;
  private final Command stationOneToPostACommand;
  private final Command stationTwoToPostBCommand;
  private final Command stationTwoToPostCCommand;
  private final Command stationTwoToPostDCommand;
  // Cross the Line Paths
  private final Command leftCrossTheLineCommand;
  private final Command middleCrossTheLineCommand;
  private final Command rightCrossTheLineCommand;

  private static Paths instance;

  public static Paths getInstance() {
    if (instance == null) {
      instance = new Paths();
    }
    return instance;
  }

  public Paths() {
    try {
      // Robot to Post Paths
      robotOneToPostJCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post E").mirrorPath());
      robotFourToPostECommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 4 to Post E"));
      robotTwoToPostHCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Robot 2 to Post H"));
      // Post to Station Paths
      postJToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post J to Station 1"));
      postHToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post H to Station 1"));
      postKToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post K to Station 1"));
      postLToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post L to Station 1"));
      postEToStationOneCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post E to Station 1"));
      postBToStationTwoCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post B to Station 2"));
      postCToStationTwoCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post C to Station 2"));
      postDToStationTwoCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Post D to Station 2"));
      // Station to Post
      stationOneToPostKCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 1 to Post K"));
      stationOneToPostLCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 1 to Post L"));
      stationOneToPostACommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 1 to Post A"));
      stationTwoToPostBCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 2 to Post B"));
      stationTwoToPostCCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 2 to Post C"));
      stationTwoToPostDCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Station 2 to Post D"));
      // Cross The Line Paths
      leftCrossTheLineCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Left Cross the Line"));
      middleCrossTheLineCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Middle Cross the Line"));
      rightCrossTheLineCommand =
          AutoBuilder.followPath(PathPlannerPath.fromPathFile("Right Cross the Line"));

    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  // Robot to Post Paths
  public Command getRobotOneToPostJCommand() {
    return robotOneToPostJCommand;
  }

  public Command getRobotFourToPostECommand() {
    return robotFourToPostECommand;
  }

  public Command getRobotTwoToPostHCommand() {
    return robotTwoToPostHCommand;
  }

  // Post to Station Paths
  public Command getPostJToStationOneCommand() {
    return postJToStationOneCommand;
  }

  public Command getPostKToStationOneCommand() {
    return postKToStationOneCommand;
  }

  public Command getPostLToStationOneCommand() {
    return postLToStationOneCommand;
  }

  public Command getPostEToStationOneCommand() {
    return postEToStationOneCommand;
  }

  public Command getPostBToStationTwoCommand() {
    return postBToStationTwoCommand;
  }

  public Command getPostCToStationTwoCommand() {
    return postCToStationTwoCommand;
  }

  // Station to Post Paths
  public Command getStationOneToPostKCommand() {
    return stationOneToPostKCommand;
  }

  public Command getStationOneToPostLCommand() {
    return stationOneToPostLCommand;
  }

  public Command getStationOneToPostACommand() {
    return stationOneToPostACommand;
  }

  public Command getStationTwoToPostBCommand() {
    return stationTwoToPostBCommand;
  }

  public Command getStationTwoToPostCCommand() {
    return stationTwoToPostCCommand;
  }

  public Command getStationTwoToPostDCommand() {
    return stationTwoToPostDCommand;
  }

  // Cross The Line Paths
  public Command getLeftCrossTheLineCommand() {
    return leftCrossTheLineCommand;
  }

  public Command getMiddleCrossTheLineCommand() {
    return middleCrossTheLineCommand;
  }

  public Command getRightCrossTheLineCommand() {
    return rightCrossTheLineCommand;
  }

  public Command getPostHToStationeOneCommand() {
    return postHToStationOneCommand;
  }

  public Command getPostDToStationTwoCommand() {
    return postDToStationTwoCommand;
  }
}
