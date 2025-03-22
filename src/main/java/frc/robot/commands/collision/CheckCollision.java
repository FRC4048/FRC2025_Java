package frc.robot.commands.collision;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N2;
import frc.robot.constants.Constants;
import frc.robot.subsystems.gyro.ThreadedGyro;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

public class CheckCollision extends LoggableCommand {
  private SwerveDrivetrain drivetrain;
  private DoubleSupplier horizSupplier;
  private final DoubleSupplier vertSupplier;
  private ThreadedGyro accelerometer;
  private Vector<N2> currentVelValue;
  private Vector<N2> predictedVelValue;
  private boolean trustOdometry;

  public CheckCollision(
      ThreadedGyro accelerometer,
      SwerveDrivetrain drivetrain,
      DoubleSupplier horizSupplier,
      DoubleSupplier vertSupplier) {
    this.drivetrain = drivetrain;
    this.horizSupplier = horizSupplier;
    this.vertSupplier = vertSupplier;
    this.accelerometer = accelerometer;
    trustOdometry = true;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double horizVal = -horizSupplier.getAsDouble();
    double vertVal = -vertSupplier.getAsDouble();
    double y = MathUtil.applyDeadband(horizVal, 0.05) * Constants.MAX_VELOCITY / 10;
    double x = MathUtil.applyDeadband(vertVal, 0.05) * Constants.MAX_VELOCITY / 10;
    currentVelValue = accelerometer.getVelocityValue();
    predictedVelValue = VecBuilder.fill(x, y);
    Vector<N2> diff = currentVelValue.minus(predictedVelValue);
    if (diff.dot(diff) > Constants.COLLISION_VALUE) {
      trustOdometry = false;
    }
  }

  public boolean getTrustOdometry() {
    return trustOdometry;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
