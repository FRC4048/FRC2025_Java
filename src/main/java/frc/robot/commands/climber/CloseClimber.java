package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class CloseClimber extends LoggableCommand {

  private final ClimberSubsystem climber;
  private double startTime;

  public CloseClimber(ClimberSubsystem climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    climber.setClimberSpeed(Constants.CLIMBER_CLOSE_SPEED);
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopClimber();
  }

  @Override
  public boolean isFinished() {
    if (climber.isRetractedLimitSwitchPressed()) {
      return true;
    } else if ((Timer.getFPGATimestamp() - startTime) >= Constants.CLIMBER_TIMEOUT) {
      return true;
    }
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
