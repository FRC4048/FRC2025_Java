package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.CoralShooter.CoralShooterSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class IntakeCoral extends LoggableCommand {
  private final CoralShooterSubsystem intake;
  private final Timer timer = new Timer();

  public IntakeCoral(CoralShooterSubsystem intake) {
    addRequirements(intake);
    this.intake = intake;
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    intake.setShooterSpeed(Constants.INTAKE_MOTOR_SPEED);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stopShooterMotors();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.INTAKE_CORAL_TIMEOUT) || intake.getReverseSwitchState();
  }
}
