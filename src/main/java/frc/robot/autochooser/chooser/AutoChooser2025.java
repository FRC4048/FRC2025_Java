package frc.robot.autochooser.chooser;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.autochooser.AutoAction;
import frc.robot.autochooser.FieldLocation;
import frc.robot.autochooser.event.AutoEvent;
import frc.robot.autochooser.event.AutoEventProvider;
import frc.robot.autochooser.event.AutoEventProviderIO;
import frc.robot.commands.autos.LeftCrossTheLine;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.DoNothingCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.parser.ParseException;

public class AutoChooser2025 extends SubsystemBase implements AutoChooser {
  private final Map<AutoEvent, LoggableSequentialCommandGroup> commandMap;
  private final AutoEventProvider provider;

  public AutoChooser2025(
      AutoEventProviderIO providerIO, ElevatorSubsystem elevator, CoralSubsystem coral) {
    provider = new AutoEventProvider(providerIO, this::isValid);
    Map<AutoEvent, LoggableSequentialCommandGroup> tempMap = new HashMap<>();
    try {
      tempMap =
          Map.ofEntries(
              Map.entry(
                  new AutoEvent(AutoAction.CrossTheLine, FieldLocation.LEFT),
                  new LeftCrossTheLine(elevator, coral)));
      tempMap =
          Map.ofEntries(
              // LEFT AUTOS
              // LEFT DO NOTHING
              Map.entry(
                  new AutoEvent(AutoAction.DoNothing, FieldLocation.LEFT), new DoNothingCommand()),
              // LEFT CROSS THE LINE
              Map.entry(
                  new AutoEvent(AutoAction.CrossTheLine, FieldLocation.LEFT),
                  new LeftCrossTheLine(elevator, coral)),
              // LEFT 4 PIECE FORK
              Map.entry(
                  new AutoEvent(AutoAction.FourPieceFork, FieldLocation.LEFT),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(PathPlannerPath.fromPathFile("Left 4 Piece Fork")))),
              // LEFT 4 PIECE LINE
              Map.entry(
                  new AutoEvent(AutoAction.FourPieceLine, FieldLocation.LEFT),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(PathPlannerPath.fromPathFile("Left 4 Piece Line")))),
              // RIGHT AUTOS
              // RIGHT DO NOTHING
              Map.entry(
                  new AutoEvent(AutoAction.DoNothing, FieldLocation.RIGHT), new DoNothingCommand()),
              // RIGHT CROSS THE LINE
              Map.entry(
                  new AutoEvent(AutoAction.CrossTheLine, FieldLocation.RIGHT),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(
                          PathPlannerPath.fromPathFile("Right Cross the Line")))),
              // RIGHT 4 PIECE FORK
              Map.entry(
                  new AutoEvent(AutoAction.FourPieceFork, FieldLocation.RIGHT),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(PathPlannerPath.fromPathFile("Right 4 Piece Fork")))),
              // RIGHT 4 PIECE LINE
              Map.entry(
                  new AutoEvent(AutoAction.FourPieceLine, FieldLocation.RIGHT),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(PathPlannerPath.fromPathFile("Right 4 Piece Line")))),
              // MIDDLE AUTOS
              // MIDDLE DO NOTHING
              Map.entry(
                  new AutoEvent(AutoAction.DoNothing, FieldLocation.MIDDLE),
                  new DoNothingCommand()),
              // MIDDLE CROSS THE LINE
              Map.entry(
                  new AutoEvent(AutoAction.CrossTheLine, FieldLocation.MIDDLE),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(
                          PathPlannerPath.fromPathFile("Middle Cross the Line")))),
              // MIDDLE 1 PIECE
              Map.entry(
                  new AutoEvent(AutoAction.OnePiece, FieldLocation.MIDDLE),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(PathPlannerPath.fromPathFile("Middle 1 Piece")))),
              // MIDDLE 4 Piece Fork
              Map.entry(
                  new AutoEvent(AutoAction.FourPieceFork, FieldLocation.MIDDLE),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(PathPlannerPath.fromPathFile("Middle 4 Piece Fork")))),
              // MIDDLE 4 Piece Line
              Map.entry(
                  new AutoEvent(AutoAction.FourPieceFork, FieldLocation.MIDDLE),
                  LoggableCommandWrapper.wrap(
                      AutoBuilder.followPath(
                          PathPlannerPath.fromPathFile("Middle 4 Piece Line")))));

    } catch (ParseException | FileVersionException | IOException e) {
      tempMap = new HashMap<>();
      DriverStation.reportError(
          "Michael Broke PathPlanner", true); // Noah put this here I don't wanna change it
    } finally {
      commandMap = tempMap;
    }
  }

  @Override
  public Command getAutoCommand() {
    return commandMap.get(
        new AutoEvent(provider.getSelectedAction(), provider.getSelectedLocation()));
  }

  @Override
  public Pose2d getStartingPosition() {
    return provider.getSelectedLocation().getLocation();
  }

  protected boolean isValid(AutoAction action, FieldLocation location) {
    return commandMap.containsKey(new AutoEvent(action, location));
  }

  @Override
  public void periodic() {
    provider.updateInputs();
  }

  public AutoEventProvider getProvider() {
    return provider;
  }
}
