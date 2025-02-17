package frc.robot.commands.byebye;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SpinByeByeRoller extends LoggableCommand {
  private final AlgaeByeByeRollerSubsystem byebyeRoller;
  private final Timer timer;

  public SpinByeByeRoller(AlgaeByeByeRollerSubsystem byebyeRoller) {
    timer = new Timer();
    this.byebyeRoller = byebyeRoller;
    addRequirements(byebyeRoller);
  }

  @Override
  public void initialize() {
    timer.restart();
    byebyeRoller.setSpeed(Constants.BYEBYE_ROLLER_SPEED);
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
      return true;
    }
    return false;
  }
}
