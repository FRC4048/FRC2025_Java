package frc.robot.commands.drivetrain;

import frc.robot.autochooser.FieldLocation;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.LoggableCommand;

public class SetInitOdom extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;

  public SetInitOdom(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    drivetrain.setGyroOffset(
        FieldLocation.ROBOT_POS_1.getLocation().getRotation().getDegrees()11);
    drivetrain.resetOdometry(FieldLocation.ROBOT_POS_1.getLocation());
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
