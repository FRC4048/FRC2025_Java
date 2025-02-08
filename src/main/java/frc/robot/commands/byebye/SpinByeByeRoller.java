package frc.robot.commands.byebye;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.utils.logging.LoggableCommand;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.constants.Constants;

public class SpinByeByeRoller extends LoggableCommand {
  private final AlgaeByeByeRollerSubsystem byebyeRoller;
  private final Timer timer;
  private final TimeoutLogger timeoutCounter;

  public SpinByeByeRoller(AlgaeByeByeRollerSubsystem byebyeRoller) {
    timeoutCounter = new TimeoutLogger("Spin Bye Bye Roller");
    timer = new Timer();
    this.byebyeRoller = byebyeRoller;
    addRequirements(byebyeRoller);
  }

  @Override
  public void initialize() {
    byebyeRoller.setSpeed(Constants.BYEBYE_ROLLER_SPEED);
    timer.restart();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    byebyeRoller.stopMotors();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.BYEBYE_SPIN_ROLLER_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
  return false;
  }
}