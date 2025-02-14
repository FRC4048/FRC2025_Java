package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ResetClimber extends LoggableCommand {

  private final ClimberSubsystem climber;
  private final TimeoutLogger timeoutCounter;
  private final Timer timer;

  public ResetClimber(ClimberSubsystem climber) {
    this.climber = climber;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("Reset Climber");
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    climber.setClimberSpeed(-Constants.CLIMBER_RISE_SPEED); // assuming positive is forward
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopClimber();
    climber.resetClimberEncoder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (climber.isRetractedLimitSwitchPressed()) {
      return true;
    } else if (timer.hasElapsed(Constants.RESET_CLIMBER_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
