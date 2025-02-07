package frc.robot.commands.subsystemTests;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.CoralAngle.CoralAngleSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class CoralAngleTest extends LoggableCommand {
  private final CoralAngleSubsystem coralAngle;
  private final double angle;
  private double timeStart;

  public CoralAngleTest(CoralAngleSubsystem coralAngle, double angle) {
    this.angle = angle;
    this.coralAngle = coralAngle;
    addRequirements(coralAngle);
  }

  @Override
  public void initialize() {
    coralAngle.setAngle(angle);
    timeStart = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    coralAngle.stopTiltMotors();
  }

  @Override
  public boolean isFinished() {
    return (Timer.getFPGATimestamp() - timeStart >= 10)
        || coralAngle.getForwardSwitchState()
        || coralAngle.getReverseSwitchState();
  }
}
