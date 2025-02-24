package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;

public class FineAlign extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private Pose2d targetPose;
  private final Timer timer = new Timer();

  public FineAlign(SwerveDrivetrain drivetrain, Pose2d targetPose) {
    this.drivetrain = drivetrain;
    this.targetPose = targetPose;
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    drivetrain.drive(drivetrain.calculateSpeedsTowardsPoint(targetPose));
  }

  @Override
  public boolean isFinished() {
    return drivetrain.atTarget(targetPose) || timer.hasElapsed(5);
  }
}
