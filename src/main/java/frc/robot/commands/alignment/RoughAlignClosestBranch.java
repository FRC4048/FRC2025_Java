package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.AlignmentPosition;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.PathPlannerUtils;
import frc.robot.utils.logging.commands.LoggableCommand;
import org.littletonrobotics.junction.Logger;

public class RoughAlignClosestBranch extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final Timer timer = new Timer();
  private Pose2d position;

  public RoughAlignClosestBranch(SwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    timer.restart();
    AlignmentPosition alignmentTarget =
        AlignmentPosition.getClosest(drivetrain.getPose().getTranslation());
    Logger.recordOutput("TargetReefRoughAlignment", alignmentTarget);
    Pose2d position = alignmentTarget.getPathPlannerPosition();
    Logger.recordOutput("TargetReefRoughPose", position);
    PathPlannerUtils.pathToPose(position, 0).schedule();
  }

  @Override
  public boolean isFinished() {
    if (drivetrain.getPose().getTranslation().getDistance(position.getTranslation())
        < Constants.ALIGNMENT_DISTANCE_THRESHOLD) {
      return true;
    } else {
      return false;
    }
  }
}
