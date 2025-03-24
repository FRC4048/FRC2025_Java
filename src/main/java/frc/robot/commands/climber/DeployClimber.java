package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class DeployClimber extends LoggableCommand {
  private final ClimberSubsystem climber;
  private final TimeoutLogger timeoutCounter;
  private final Timer timer;

  public DeployClimber(ClimberSubsystem climber) {
    this.climber = climber;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("DeployClimber");
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    climber.setLimitSwitchState(false);
    timer.restart();
  }

  @Override
  public void execute() {
    climber.setClimberSpeed(Constants.CLIMBER_PHASE1_SPEED);
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopClimber();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.CLIMBER_DEPLOY_HARPOON_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return climber.isRetractedLimitSwitchPressed();
  }
}
