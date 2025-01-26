package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class IntakeCoral extends LoggableCommand {
  private final CoralSubsystem intake;
  private final Timer timer = new Timer();
  private final double motorRunTime; // temporary until  done testing

  public IntakeCoral(CoralSubsystem intake, double motorRunTime) {
    addRequirements(intake);
    this.intake = intake;
    this.motorRunTime = motorRunTime;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    intake.setShooterSpeed(Constants.INTAKE_MOTOR_SPEED);
    intake.setAngle(Constants.INTAKE_TILT_SPEED);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stopShooterMotors();
    intake.stopTiltMotors();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(motorRunTime);
  }
}
