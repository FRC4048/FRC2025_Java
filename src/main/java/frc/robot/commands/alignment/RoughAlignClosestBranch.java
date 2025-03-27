package frc.robot.commands.alignment;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
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
    try {
      AlignmentPosition alignmentTarget =
          AlignmentPosition.getClosest(drivetrain.getPose().getTranslation());
      Logger.recordOutput("TargetReefRoughAlignment", alignmentTarget);
      Pose2d position = alignmentTarget.getPathPlannerPosition();
      Logger.recordOutput("TargetReefRoughPose", position);
      PathPlannerUtils.pathToPose(position, 0).schedule();
    } catch (Exception e) {
      DriverStation.reportError(e.getMessage(), true);
    }
  }

  @Override
  public boolean isFinished() {
    return drivetrain.getPose().getTranslation().getDistance(position.getTranslation())
            < Constants.AUTO_ALIGN_THRESHOLD
        || timer.hasElapsed(Constants.AUTO_ALIGN_TIMEOUT);
  }
}
