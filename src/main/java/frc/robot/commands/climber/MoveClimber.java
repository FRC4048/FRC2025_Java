package frc.robot.commands.climber;

import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

public class MoveClimber extends LoggableCommand {

  private final ClimberSubsystem climber;
  private final DoubleSupplier targetSupplier;

  public MoveClimber(ClimberSubsystem climber, DoubleSupplier targetSupplier) {
    this.climber = climber;
    this.targetSupplier = targetSupplier;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  @Override
  public void execute() {
    climber.setClimberSpeed(targetSupplier.getAsDouble()/3);
  }

  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
