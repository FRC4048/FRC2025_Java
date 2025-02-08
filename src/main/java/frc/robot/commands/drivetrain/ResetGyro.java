package frc.robot.commands.drivetrain;

import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ResetGyro extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;

  public ResetGyro(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    super.initialize();
    drivetrain.resetGyro();
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
