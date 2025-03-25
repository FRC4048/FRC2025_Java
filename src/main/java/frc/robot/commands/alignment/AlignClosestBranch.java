package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.constants.AlignmentPosition;
import frc.robot.constants.Constants;
import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import org.littletonrobotics.junction.Logger;

public class AlignClosestBranch extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final LightStrip light;
  private LoggableCommand followTrajectory;
  private final Timer timer = new Timer();

  public AlignClosestBranch(SwerveDrivetrain drivetrain, LightStrip lightStrip) {
    this.drivetrain = drivetrain;
    this.light = lightStrip;
    addRequirements(drivetrain, lightStrip);
  }

  @Override
  public void initialize() {
    timer.restart();
    AlignmentPosition alignmentTarget =
        AlignmentPosition.getClosest(drivetrain.getPose().getTranslation());
    Logger.recordOutput("TargetReefAlignment", alignmentTarget);
    Pose2d position = alignmentTarget.getPosition();
    Logger.recordOutput("TargetReefPose", position);
    drivetrain.setFocusedApriltag(alignmentTarget.getTag());
    // spotless:off (shhhh don't tell anyone about the linter bypass)
    followTrajectory = LoggableCommandWrapper.wrap(
            drivetrain.generateTrajectoryCommand(position)
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
    if (followTrajectory.isFinished()) {
      light.setPattern(BlinkinPattern.LAWN_GREEN);
      return true;
    } else if (timer.hasElapsed(Constants.AUTO_ALIGN_TIMEOUT)) {
      light.setPattern(BlinkinPattern.HEARTBEAT_RED);
      return true;
    } else {
      return false;
    }
  }
}
