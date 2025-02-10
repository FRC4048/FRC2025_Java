package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class IntakeCoral extends LoggableCommand {
  private final CoralSubsystem intake;
  private final TimeoutLogger timoutCounter;
  private final Timer timer = new Timer();

  public IntakeCoral(CoralSubsystem intake) {
    addRequirements(intake);
    this.intake = intake;
    timoutCounter = new TimeoutLogger("Intake Coral");
  }

  @Override
  public void initialize() {
    timer.restart();
    intake.enableOrDisableLimitSwitch(true);
  }

  @Override
  public void execute() {
    intake.setShooterSpeed(Constants.INTAKE_MOTOR_SPEED);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stopShooterMotors();
    intake.stopTiltMotors();
    intake.enableOrDisableLimitSwitch(false);
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.INTAKE_CORAL_TIMEOUT)) {
      timoutCounter.increaseTimeoutCount();
      return true;
    }
    return intake.getReverseSwitchState();
  }
}
