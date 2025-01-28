package frc.robot.commands.AlgaeExtender;

import frc.robot.constants.Constants2025;
import frc.robot.subsystems.hihiExtender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiRoller.HihiRollerSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class RetractAlgaeExtender extends LoggableCommand {
  /** Creates a new ShootCoral. */
  private final HihiExtenderSubsystem hihiExtender;

  private final HihiRollerSubsystem hihiRoller;

  public RetractAlgaeExtender(HihiExtenderSubsystem hihiExtender, HihiRollerSubsystem hihiRoller) {
    this.hihiExtender = hihiExtender;
    this.hihiRoller = hihiRoller;
    addRequirements(hihiExtender, hihiRoller);
  }

  @Override
  public void initialize() {
    hihiExtender.setExtenderSpeed(-1 * Constants2025.EXTENDER_MOTOR_SPEED);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    hihiExtender.stopExtenderMotors();
  }

  @Override
  public boolean isFinished() {
    return hihiExtender.getReverseSwitchState();
  }
}
