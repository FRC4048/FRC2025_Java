package frc.robot.commands.byebye;

import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class StopByeByeMotors extends LoggableCommand {
  private final AlgaeByeByeRollerSubsystem byebyeRoller;

  public StopByeByeMotors(AlgaeByeByeRollerSubsystem byebyeRoller) {
    this.byebyeRoller = byebyeRoller;
    addRequirements(byebyeRoller);
  }

  @Override
  public void initialize() {
    byebyeRoller.stopMotors();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
