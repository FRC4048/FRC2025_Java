package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.constants.AlignmentPositions;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.auto.PathPlannerUtils;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.logging.commands.LoggableCommandWrapper;
import frc.robot.utils.logging.commands.LoggableSequentialCommandGroup;
import frc.robot.utils.math.PoseUtils;

public class AlignClosestBranch extends LoggableCommand {
  private Pose2d targetPosition;
  private final SwerveDrivetrain drivetrain;
  private LoggableSequentialCommandGroup driveSequence;
  private final Timer timer = new Timer();

  public AlignClosestBranch(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    timer.restart();
    targetPosition = AlignmentPositions.getClosest(drivetrain.getPose());
    LoggableSequentialCommandGroup sequence = new LoggableSequentialCommandGroup(
            LoggableCommandWrapper.wrap(PathPlannerUtils.pathToPose(targetPosition, 0.0)).withBasicName("GeneralAlign"),
            new FineAlign(drivetrain, targetPosition)
    ).withBasicName("PathPlannerToBranch");
    sequence.setParent(this);
    sequence.schedule();
  }

  @Override
  public void end(boolean interrupted) {
    if (CommandScheduler.getInstance().isScheduled(driveSequence)){
      CommandScheduler.getInstance().cancel(driveSequence);
    }
  }

  @Override
  public boolean isFinished() {
    return driveSequence.isFinished() | timer.hasElapsed(10);
  }
}
