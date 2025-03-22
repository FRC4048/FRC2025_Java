package frc.robot.commands.climber;

import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ResetClimberEncoder extends LoggableCommand {

  private final ClimberSubsystem climber;

  public ResetClimberEncoder(ClimberSubsystem climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.resetClimberEncoder();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
