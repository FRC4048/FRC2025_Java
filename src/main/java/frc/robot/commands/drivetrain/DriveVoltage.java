package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

/**
 * This class can be used to calculate kS value for {@link
 * edu.wpi.first.math.controller.SimpleMotorFeedforward SimpleMotorFeedforward}.<br>
 * Keep applying volts until the motors are able to move the robot at all. <br>
 * kS represents the volts need to overcome static friction
 */
public class DriveVoltage extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final DoubleSupplier volts;
  private final double time;
  private final Timer timer;

  public DriveVoltage(SwerveDrivetrain drivetrain, DoubleSupplier volts, double time) {
    this.drivetrain = drivetrain;
    this.volts = volts;
    this.time = time;
    this.timer = new Timer();
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    drivetrain.applyVolts(volts.getAsDouble());
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(time);
  }
}
