package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

public class RobotSlide extends LoggableCommand {

  private SwerveDrivetrain drivetrain;
  private DoubleSupplier horizSupplier;
  private final DoubleSupplier vertSupplier;

  public RobotSlide(
      SwerveDrivetrain drivetrain, DoubleSupplier horizSupplier, DoubleSupplier vertSupplier) {
    this.drivetrain = drivetrain;
    this.horizSupplier = horizSupplier;
    this.vertSupplier = vertSupplier;
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    double horizVal = -horizSupplier.getAsDouble();
    double vertVal = -vertSupplier.getAsDouble();
    double y = MathUtil.applyDeadband(horizVal, 0.05) * Constants.MAX_VELOCITY / 8;
    double x = MathUtil.applyDeadband(vertVal, 0.05) * Constants.MAX_VELOCITY / 8;
    ChassisSpeeds speeds = drivetrain.createChassisSpeeds(x, y, 0, DriveMode.ROBOT_CENTRIC);
    drivetrain.drive(speeds);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
