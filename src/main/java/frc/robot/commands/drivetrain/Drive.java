package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import frc.robot.RobotContainer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.LoggableCommand;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class Drive extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;

  private final DoubleSupplier fwdSupplier;
  private final DoubleSupplier strSupplier;
  private final DoubleSupplier rtSupplier;
  private boolean shouldFlip;
  private final Supplier<DriveMode> driveMode;
  private Alert alert = new Alert("Drive command ran", AlertType.kInfo);

  public Drive(
      SwerveDrivetrain drivetrain,
      DoubleSupplier fwdSupplier,
      DoubleSupplier strSupplier,
      DoubleSupplier rtSupplier,
      Supplier<DriveMode> driveMode) {
    this.drivetrain = drivetrain;
    this.fwdSupplier = fwdSupplier;
    this.strSupplier = strSupplier;
    this.rtSupplier = rtSupplier;
    this.driveMode = driveMode;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    alert.set(true);
    this.shouldFlip = RobotContainer.isRedAlliance();
  }

  @Override
  public void execute() {
    double fwd = MathUtil.applyDeadband(fwdSupplier.getAsDouble(), 0.05) * Constants.MAX_VELOCITY;
    double str = MathUtil.applyDeadband(strSupplier.getAsDouble(), 0.05) * Constants.MAX_VELOCITY;
    ChassisSpeeds driveStates;
    double rcw = MathUtil.applyDeadband(rtSupplier.getAsDouble(), 0.05) * Constants.MAX_VELOCITY;
    drivetrain.setFacingTarget(false);
    driveStates =
        drivetrain.createChassisSpeeds(
            fwd * (shouldFlip ? 1 : -1), str * (shouldFlip ? 1 : -1), -rcw, driveMode.get());
    drivetrain.drive(driveStates);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
