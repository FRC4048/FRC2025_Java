package frc.robot.commands.climber;

import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class StopClimber extends LoggableCommand {
  private final ClimberSubsystem climber;

  public StopClimber(ClimberSubsystem climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    climber.stopClimber();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
