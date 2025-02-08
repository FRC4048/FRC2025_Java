package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class RetractHiHi extends LoggableCommand {
  private final HihiExtenderSubsystem hihiExtender;
  private Timer timer;
  private final TimeoutLogger timeoutCounter;

  public RetractHiHi(HihiExtenderSubsystem hihiExtender) {
    this.hihiExtender = hihiExtender;
    timeoutCounter = new TimeoutLogger("RetractHIHI");
    addRequirements(hihiExtender);
    timer = new Timer();
  }

  @Override
  public void initialize() {
    hihiExtender.setExtenderSpeed(Constants.HIHI_RETRACT_SPEED);
    timer.restart();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    hihiExtender.stopExtenderMotors();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.HIHI_RETRACT_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return (hihiExtender.getReverseSwitchState());
  }
}
