package frc.robot.commands.hihi;

import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ExtendHiHi extends LoggableCommand {
  private final HihiExtenderSubsystem hihiExtender;

  public ExtendHiHi(HihiExtenderSubsystem hihiExtender) {
    this.hihiExtender = hihiExtender;
    addRequirements(hihiExtender);
  }

  @Override
  public void initialize() {
    hihiExtender.setExtenderPosition(Constants.HIHI_EXTEND_POSITION);
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
