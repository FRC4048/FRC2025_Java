package frc.robot.commands.Intake;

import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class StopIntake extends LoggableCommand {
  private final CoralSubsystem intake;

  public StopIntake(CoralSubsystem intake) {
    addRequirements(intake);
    this.intake = intake;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.stopShooterMotors();
    intake.stopTiltMotors();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
