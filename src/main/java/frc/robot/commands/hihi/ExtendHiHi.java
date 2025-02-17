package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ExtendHiHi extends LoggableCommand {
  private final HihiExtenderSubsystem hihiExtender;
  private Timer timer;
  private final TimeoutLogger timeoutCounter;

  public ExtendHiHi(HihiExtenderSubsystem hihiExtender) {
    this.hihiExtender = hihiExtender;
    timeoutCounter = new TimeoutLogger("ExtendHiHi");
    timer = new Timer();
    addRequirements(hihiExtender);
  }

  @Override
  public void initialize() {
    hihiExtender.setExtenderPosition(Constants.HIHI_EXTEND_POSITION);
    timer.restart();
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
