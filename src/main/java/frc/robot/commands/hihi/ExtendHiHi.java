package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class ExtendHiHi extends LoggableCommand {
  private final HihiExtenderSubsystem hihiExtender;
  private double time;

  public ExtendHiHi(HihiExtenderSubsystem hihiExtender) {
    this.hihiExtender = hihiExtender;
    addRequirements(hihiExtender);
  }

  @Override
  public void initialize() {
    hihiExtender.setExtenderSpeed(Constants.HIHI_EXTEND_SPEED);
    time = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    hihiExtender.stopExtenderMotors();
  }

  @Override
  public boolean isFinished() {
    return (hihiExtender.getForwardSwitchState()
        || Timer.getFPGATimestamp() - time >= Constants.HIHI_EXTENDER_TIMEOUT);
  }
}
