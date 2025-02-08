package frc.robot.commands.byebye;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.utils.logging.LoggableCommand;
import frc.robot.constants.Constants;

public class SpinByeByeRoller extends LoggableCommand {
  private final AlgaeByeByeRollerSubsystem byebyeRoller;
  private double time;

  public SpinByeByeRoller(AlgaeByeByeRollerSubsystem byebyeRoller) {
    this.byebyeRoller = byebyeRoller;
    addRequirements(byebyeRoller);
  }

  @Override
  public void initialize() {
    byebyeRoller.setSpeed(Constants.BYEBYE_ROLLER_SPEED);
    time = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    byebyeRoller.stopMotors();
  }

  @Override
  public boolean isFinished() {
    return (Timer.getFPGATimestamp() - time >= Constants.BYEBYE_SPIN_ROLLER_TIMEOUT);
  }
}