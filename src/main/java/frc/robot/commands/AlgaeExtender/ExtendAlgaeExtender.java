package frc.robot.commands.AlgaeExtender;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiExtender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiRoller.HihiRollerSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class ExtendAlgaeExtender extends LoggableCommand {
  private final HihiExtenderSubsystem hihiExtender;
  private final HihiRollerSubsystem hihiRoller;
  private double time;

  public ExtendAlgaeExtender(HihiExtenderSubsystem hihiExtender, HihiRollerSubsystem hihiRoller) {
    this.hihiExtender = hihiExtender;
    this.hihiRoller = hihiRoller;
    addRequirements(hihiExtender, hihiRoller);
  }

  @Override
  public void initialize() {
    hihiExtender.setExtenderSpeed(Constants.HIHI_EXTENDER_MOTOR_EXTEND_SPEED);
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
