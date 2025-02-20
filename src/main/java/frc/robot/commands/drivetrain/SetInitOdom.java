package frc.robot.commands.drivetrain;

import frc.robot.autochooser.chooser.AutoChooser2025;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SetInitOdom extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final AutoChooser2025 autoChooser;

  public SetInitOdom(SwerveDrivetrain drivetrain, AutoChooser2025 autoChooser) {
    this.drivetrain = drivetrain;
    this.autoChooser = autoChooser;
  }

  @Override
  public void initialize() {
    drivetrain.setGyroOffset(autoChooser.getStartingPosition().getRotation().getDegrees());
    drivetrain.resetOdometry(autoChooser.getStartingPosition());
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
