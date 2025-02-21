package frc.robot.commands.autos;

import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class Paths {
  private final PathPlannerPath robotOneToPostJCommand;
  private final PathPlannerPath postJToStationOneCommand;
  private final PathPlannerPath stationOneToPostKCommand;
  private final PathPlannerPath postKToStationOne;
  private final PathPlannerPath middleOnePieceCommand;
  private final PathPlannerPath middleFourPieceForkCommand;
  private final PathPlannerPath middleFourPieceLineCommand;
    private final PathPlannerPath rightCrossTheLineCommand;
    private final PathPlannerPath rightFourPieceForkCommand;
    private final PathPlannerPath rightFourPieceLineCommand;
  
    private static Paths instance;
  
    public static Paths getInstance() {
      if (instance == null) {
        instance = new Paths();
      }
      return instance;
    }
  
    public Paths() {
      try {
        robotOneToPostJCommand = PathPlannerPath.fromPathFile("Robot 1 to Post J");
        postJToStationOneCommand = PathPlannerPath.fromPathFile("Post J to Station 1");
        stationOneToPostKCommand = PathPlannerPath.fromPathFile("Station 1 to Post K");
        postKToStationOne =  PathPlannerPath.fromPathFile("Post K to Station 1");
        middleFourPieceLineCommand = PathPlannerPath.fromPathFile("Middle 1 Piece");
        middleFourPieceForkCommand = PathPlannerPath.fromPathFile("Middle 4 Piece Fork ");
        middleOnePieceCommand = PathPlannerPath.fromPathFile("Middle One Piece");
        rightCrossTheLineCommand = PathPlannerPath.fromPathFile("Right Cross The Line");
        rightFourPieceForkCommand = PathPlannerPath.fromPathFile("Right Four Piece Fork");
        rightFourPieceLineCommand = PathPlannerPath.fromPathFile("Right 4 Piece Line");
        
      

    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public PathPlannerPath getRobotOneToPostJCommand() {
    return robotOneToPostJCommand;
  }

  public PathPlannerPath getPostJToStationOneCommand() {
    return postJToStationOneCommand;
  }

  public PathPlannerPath getStationOneToPostKCommand() {
    return stationOneToPostKCommand;
  }

  public PathPlannerPath getPostKToStationOne() {
    return postKToStationOne;
  }

  public PathPlannerPath getMiddleOnePieceCommand() {
    return middleOnePieceCommand;
  }

  public PathPlannerPath getMiddleFourPieceForkCommand() {
    return middleFourPieceForkCommand;
  }

  public PathPlannerPath getMiddleFourPieceLineCommand() {
    return middleFourPieceLineCommand;
  }

  public PathPlannerPath getRightCrossTheLine() {
    return rightCrossTheLineCommand;
  }

  public PathPlannerPath getRightFourPieceLineCommand() {
    return rightFourPieceForkCommand;
  }

  public PathPlannerPath getRightFourPieceForkCommand() {
    return rightFourPieceLineCommand;
  }
}
