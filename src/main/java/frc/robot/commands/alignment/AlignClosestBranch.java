package frc.robot.commands.alignment;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.constants.AlignmentPosition;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import org.littletonrobotics.junction.Logger;

public class AlignClosestBranch extends LoggableCommand {
  private AlignmentPosition alignmentTarget;
  private final SwerveDrivetrain drivetrain;
  private LoggableCommand followTrajectory;
  private final Timer timer = new Timer();

  public AlignClosestBranch(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    timer.restart();
    alignmentTarget = AlignmentPosition.getClosest(drivetrain.getPose().getTranslation());
    Logger.recordOutput("TargetReefAlignment", alignmentTarget);
    Logger.recordOutput("TargetReefPose", alignmentTarget.getPosition());
    drivetrain.setFocusedApriltag(alignmentTarget.getTag());
    // spotless:off (shhhh don't tell anyone about the linter bypass)
    followTrajectory = LoggableCommandWrapper.wrap(
            drivetrain.generateTrajectoryCommand(alignmentTarget.getPosition())
    ).withBasicName("FollowAlignToBranchTrajectory");
    // spotless:on
    followTrajectory.setParent(this);
    followTrajectory.schedule();
  }

  @Override
  public void end(boolean interrupted) {
    if (CommandScheduler.getInstance().isScheduled(followTrajectory)) {
      CommandScheduler.getInstance().cancel(followTrajectory);
    }
    drivetrain.exitFocusMode();
  }

  @Override
  public boolean isFinished() {
    return followTrajectory.isFinished() || timer.hasElapsed(Constants.AUTO_ALIGN_TIMEOUT);
  }
}
