package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class StartAligner extends LoggableCommand {
  private final CoralSubsystem intake;
  private final Timer timer = new Timer();
  private double speed;

  public StartAligner(CoralSubsystem intake, double speed) {
    this.intake = intake;
    this.speed = speed;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    intake.setShooterSpeed(speed);
  }

  @Override
  public void end(boolean interrupted) {
    intake.setAlignerSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.INTAKE_CORAL_TIMEOUT);
  }
}
