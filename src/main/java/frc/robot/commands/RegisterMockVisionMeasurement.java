package frc.robot.commands;

import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;

public class RegisterMockVisionMeasurement extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;

  public RegisterMockVisionMeasurement(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    drivetrain.addMockVisionMeasurement();
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
