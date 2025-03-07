package frc.robot.commands.drivetrain;

import static edu.wpi.first.wpilibj.DriverStation.Alliance;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class Drive extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final DoubleSupplier fwdSupplier;
  private final DoubleSupplier strSupplier;
  private final DoubleSupplier rtSupplier;
  private final Supplier<DriveMode> driveMode;

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
  public void execute() {
    Alliance alliance = Robot.getAllianceColor().orElse(null);
    if (alliance == null) {
      DriverStation.reportError(
          "THIS IS BAD!!!, ALLIANCE COLOR IS NULL SO DEFAULTING TO BLUE", true);
      alliance = Alliance.Blue;
    }
    boolean shouldFlip = alliance == Alliance.Red;
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
