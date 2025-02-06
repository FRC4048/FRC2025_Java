package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class RetractHiHi extends LoggableCommand {
  private final HihiExtenderSubsystem hihiExtender;
  private final HihiRollerSubsystem hihiRoller;
  private double time;

  public RetractHiHi(HihiExtenderSubsystem hihiExtender, HihiRollerSubsystem hihiRoller) {
    this.hihiExtender = hihiExtender;
    this.hihiRoller = hihiRoller;
    addRequirements(hihiExtender, hihiRoller);
  }

  @Override
  public void initialize() {
    hihiExtender.setExtenderSpeed(Constants.HIHI_RETRACT_SPEED);
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
    return (hihiExtender.getReverseSwitchState()
        || Timer.getFPGATimestamp() - time >= Constants.HIHI_EXTENDER_TIMEOUT);
  }
}
