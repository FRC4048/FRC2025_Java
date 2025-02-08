package frc.robot.commands.subsystemtests;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.CoralShooter.CoralShooterSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class CoralShooterTest extends LoggableCommand {
  private final CoralShooterSubsystem coralShooter;
  private final double speedMotors;
  private double timeStart;

  public CoralShooterTest(CoralShooterSubsystem coralShooter, double speedMotors) {
    this.speedMotors = speedMotors;
    this.coralShooter = coralShooter;
    addRequirements(coralShooter);
  }

  @Override
  public void initialize() {
    coralShooter.setShooterSpeed(speedMotors);
    timeStart = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    coralShooter.stopShooterMotors();
  }

  @Override
  public boolean isFinished() {
    return (Timer.getFPGATimestamp() - timeStart >= 10);
  }
}
