package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.constants.AlignmentPositions;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import org.littletonrobotics.junction.Logger;

public class AlignClosestBranch extends LoggableCommand {
  private Pose2d targetPosition;
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
    targetPosition = AlignmentPositions.getClosest(drivetrain.getPose());
    Logger.recordOutput("TargetReefPose", targetPosition);
    followTrajectory =
        LoggableCommandWrapper.wrap(drivetrain.generateTrajectoryCommand(targetPosition));
    followTrajectory.setParent(this);
    followTrajectory.schedule();
  }

  @Override
  public void end(boolean interrupted) {
    if (CommandScheduler.getInstance().isScheduled(followTrajectory)) {
      CommandScheduler.getInstance().cancel(followTrajectory);
    }
  }

  @Override
  public boolean isFinished() {
    return followTrajectory.isFinished() | timer.hasElapsed(10);
  }
}
