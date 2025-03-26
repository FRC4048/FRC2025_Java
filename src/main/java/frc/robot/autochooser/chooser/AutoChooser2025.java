package frc.robot.autochooser.chooser;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.autochooser.AutoAction;
import frc.robot.autochooser.FieldLocation;
import frc.robot.autochooser.event.AutoEvent;
import frc.robot.autochooser.event.AutoEventProvider;
import frc.robot.autochooser.event.AutoEventProviderIO;
import frc.robot.commands.autos.*;
import frc.robot.commands.drivetrain.RobotCentricDrive;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.DoNothingCommand;
import java.util.Map;

public class AutoChooser2025 extends SubsystemBase implements AutoChooser {
  private final Map<AutoEvent, Command> commandMap;
  private final AutoEventProvider provider;
  private final SwerveDrivetrain drivetrain;

  public AutoChooser2025(
      AutoEventProviderIO providerIO,
      SwerveDrivetrain drivetrain,
      ElevatorSubsystem elevator,
      CoralSubsystem coral,
      LightStrip lightStrip,
      AlgaeByeByeTiltSubsystem byeBye) {
    provider = new AutoEventProvider(providerIO, this::isValid);
    this.drivetrain = drivetrain;
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
                new RobotCentricDrive(drivetrain, 0.25, 3)),
            Map.entry(
                new AutoEvent(AutoAction.CROSS_THE_LINE, FieldLocation.MIDDLE),
                new RobotCentricDrive(drivetrain, 0.25, 3)),
            Map.entry(
                new AutoEvent(AutoAction.CROSS_THE_LINE, FieldLocation.RIGHT),
                new RobotCentricDrive(drivetrain, 0.25, 3)),
            Map.entry(
                new AutoEvent(AutoAction.TWO_PIECE_HIGH, FieldLocation.RIGHT),
                new RightFourPieceFork(
                    elevator, coral, lightStrip, ElevatorPosition.LEVEL4, ElevatorPosition.LEVEL4)),
            Map.entry(
                new AutoEvent(AutoAction.TWO_PIECE_LOW, FieldLocation.RIGHT),
                new RightFourPieceFork(
                    elevator, coral, lightStrip, ElevatorPosition.LEVEL2, ElevatorPosition.LEVEL4)),
            Map.entry(
                new AutoEvent(AutoAction.ONE_PIECE, FieldLocation.MIDDLE),
                new MiddleOnePiece(elevator, coral, lightStrip)));
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
