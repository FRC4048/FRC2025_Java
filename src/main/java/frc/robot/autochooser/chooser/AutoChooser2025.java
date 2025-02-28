package frc.robot.autochooser.chooser;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.autochooser.AutoAction;
import frc.robot.autochooser.FieldLocation;
import frc.robot.autochooser.event.AutoEvent;
import frc.robot.autochooser.event.AutoEventProvider;
import frc.robot.autochooser.event.AutoEventProviderIO;
import frc.robot.commands.autos.LeftCrossTheLine;
import frc.robot.commands.autos.MiddleCrossTheLine;
import frc.robot.commands.autos.RightCrossTheLine;
import frc.robot.commands.autos.RightFourPieceFork;
import frc.robot.commands.autos.RightFourPieceLine;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.logging.commands.DoNothingCommand;
import java.util.Map;

public class AutoChooser2025 extends SubsystemBase implements AutoChooser {
  private final Map<AutoEvent, Command> commandMap;
  private final AutoEventProvider provider;

  public AutoChooser2025(
      AutoEventProviderIO providerIO,
      ElevatorSubsystem elevator,
      CoralSubsystem coral,
      LightStrip lightStrip,
      AlgaeByeByeTiltSubsystem byeBye) {
    provider = new AutoEventProvider(providerIO, this::isValid);
    commandMap =
        Map.ofEntries(
            Map.entry(
                new AutoEvent(AutoAction.DO_NOTHING, FieldLocation.LEFT), new DoNothingCommand()),
            Map.entry(
                new AutoEvent(AutoAction.DO_NOTHING, FieldLocation.MIDDLE), new DoNothingCommand()),
            Map.entry(
                new AutoEvent(AutoAction.DO_NOTHING, FieldLocation.RIGHT), new DoNothingCommand()),
            Map.entry(
                new AutoEvent(AutoAction.CROSS_THE_LINE, FieldLocation.LEFT),
                new LeftCrossTheLine(elevator, coral)),
            Map.entry(
                new AutoEvent(AutoAction.CROSS_THE_LINE, FieldLocation.MIDDLE),
                new MiddleCrossTheLine(elevator, coral)),
            Map.entry(
                new AutoEvent(AutoAction.CROSS_THE_LINE, FieldLocation.RIGHT),
                new RightCrossTheLine(elevator, coral)),
            Map.entry(
                new AutoEvent(AutoAction.FOUR_PIECE_FORK, FieldLocation.RIGHT),
                new RightFourPieceFork(elevator, coral, lightStrip)),
            Map.entry(
                new AutoEvent(AutoAction.FOUR_PIECE_LINE, FieldLocation.RIGHT),
                new RightFourPieceLine(elevator, coral, lightStrip)));
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
