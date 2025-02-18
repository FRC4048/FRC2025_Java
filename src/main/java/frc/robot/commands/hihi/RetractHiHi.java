package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class RetractHiHi extends LoggableCommand {
  private final HihiExtenderSubsystem hihiExtender;
  private Timer timer;
  private final TimeoutLogger timeoutCounter;
  private int ticksAtLimit = 0;

  public RetractHiHi(HihiExtenderSubsystem hihiExtender) {
    this.hihiExtender = hihiExtender;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("RetractHIHI");
    addRequirements(hihiExtender);
  }

  @Override
  public void initialize() {
    timer.restart();
    hihiExtender.setExtenderSpeed(-.2);
    ticksAtLimit = 0;
  }

  @Override
  public void execute() {
    if (hihiExtender.getReverseSwitchState()) {
      ticksAtLimit++;
    }
  }

  @Override
  public void end(boolean interrupted) {
    hihiExtender.resetEncoder();
    hihiExtender.setExtenderPosition(0);
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(3)) {
      // Couldn't get to limit
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return (hihiExtender.getReverseSwitchState() && ticksAtLimit > 15);
  }
}
