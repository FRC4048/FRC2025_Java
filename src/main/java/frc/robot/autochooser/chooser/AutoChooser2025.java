package frc.robot.autochooser.chooser;
import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.autochooser.AutoAction;
import frc.robot.autochooser.FieldLocation;
import frc.robot.autochooser.event.AutoEvent;
import frc.robot.autochooser.event.AutoEventProvider;
import frc.robot.autochooser.event.AutoEventProviderIO;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.DoNothingCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;



public class AutoChooser2025 extends SubsystemBase implements AutoChooser {
    private final Map<AutoEvent, Command> commandMap; 
    private final AutoEventProvider provider;

    public AutoChooser2025(AutoEventProviderIO providerIO){
        provider = new AutoEventProvider(providerIO, this::isValid);
        commandMap = Map.ofEntries(
            Map.entry(new AutoEvent(AutoAction.DoNothing, FieldLocation.LEFT), new DoNothingCommand()),
            Map.entry(new AutoEvent(AutoAction.DoNothing, FieldLocation.MIDDLE), new DoNothingCommand()),
            Map.entry(new AutoEvent(AutoAction.DoNothing, FieldLocation.RIGHT), new DoNothingCommand()),
            Map.entry(new AutoEvent(AutoAction.FourPieceLine, FieldLocation.RIGHT), LoggableCommandWrapper.wrap(AutoBuilder.followPath(PathPlannerPath.fromPathFile("RightFourPieceLine")))),
            Map.entry(new AutoEvent(AutoAction.FourPieceLine, FieldLocation.LEFT), LoggableCommandWrapper.wrap(AutoBuilder.followPath(PathPlannerPath.fromPathFile("LeftFourPieceLine")))),
            Map.entry(new AutoEvent(AutoAction.OnePiece, FieldLocation.MIDDLE), LoggableCommandWrapper.wrap(AutoBuilder.followPath(PathPlannerPath.fromPathFile("MiddleOnePiece")))),
            Map.entry(new AutoEvent(AutoAction.FourPieceFork, FieldLocation.RIGHT), LoggableCommandWrapper.wrap(AutoBuilder.followPath(PathPlannerPath.fromPathFile("RightFourPieceFork")))),
            Map.entry(new AutoEvent(AutoAction.FourPieceFork, FieldLocation.LEFT), LoggableCommandWrapper.wrap(AutoBuilder.followPath(PathPlannerPath.fromPathFile("LeftFourPieceFork")))),
            Map.entry(new AutoEvent(AutoAction.FourPieceFork, FieldLocation.MIDDLE), LoggableCommandWrapper.wrap(AutoBuilder.followPath(PathPlannerPath.fromPathFile("MiddleFourPieceFork")))),
            Map.entry(new AutoEvent(AutoAction.CrossTheLine, FieldLocation.MIDDLE), LoggableCommandWrapper.wrap(AutoBuilder.followPath(PathPlannerPath.fromPathFile("CrossTheLine")))))
        
    }

    @Override
    public Command getAutoCommand() {
        return commandMap.get(new AutoEvent(provider.getSelectedAction(), provider.getSelectedLocation()));
    }

    @Override
    public Pose2d getStartingPosition() {
        return provider.getSelectedLocation().getLocation();
    }

    protected boolean isValid(AutoAction action, FieldLocation location){
        return commandMap.containsKey(new AutoEvent(action, location));
    }

    @Override
    public void periodic(){
        provider.updateInputs();
    }

    public AutoEventProvider getProvider(){
        return provider;
    }
}

