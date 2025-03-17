package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ClimbToLimit extends LoggableCommand {

  private final ClimberSubsystem climber;
  private final TimeoutLogger timeoutCounter;
  private final Timer timer;
  private final double climberSpeed;
  private boolean isLimitSwitchPressed;

  public ClimbToLimit(ClimberSubsystem climber, double climberSpeed) {
    this.climber = climber;
    this.climberSpeed = climberSpeed;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("SetClimberSpeed");
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    isLimitSwitchPressed = climber.isExtendedLimitSwitchPressed();
    climber.setLimitSwitchState(false);
    timer.restart();
  }

  @Override
  public void execute() {
    if (isLimitSwitchPressed) {
      climber.setClimberSpeed(climberSpeed);
    }
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopClimber();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.CLIMBER_PHASE2_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    } else {
      return climber.isRetractedLimitSwitchPressed() || !isLimitSwitchPressed;
    }
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
