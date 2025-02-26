package frc.robot.commands.climber;

import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class DisengageRatchet extends LoggableCommand {

  private final ClimberSubsystem climber;

  public DisengageRatchet(ClimberSubsystem climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("!!!!!!!!DISENGAGE");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.disengageRatchet();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
