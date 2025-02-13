package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class CloseClimber extends LoggableCommand {

  private final ClimberSubsystem climber;
  private final TimeoutLogger timeoutCounter;
  private final Timer timer;

  public CloseClimber(ClimberSubsystem climber) {
    this.climber = climber;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("Close Climber to limit switch");
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    timer.restart();
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
    } else if (timer.hasElapsed(Constants.CLOSE_CLIMBER_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
