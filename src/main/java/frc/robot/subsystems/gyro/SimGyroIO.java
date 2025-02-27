package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.DegreesPerSecond;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.constants.Constants;
import frc.robot.utils.motor.SparkUtil;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.ironmaple.simulation.drivesims.GyroSimulation;

public class SimGyroIO implements GyroIO {
  private final GyroSimulation gyroSimulation;
  private final AtomicBoolean shouldReset = new AtomicBoolean(false);
  private final AtomicBoolean shouldOffset = new AtomicBoolean(false);
  private final AtomicLong lastGyro;
  private final AtomicLong gyroOffset = new AtomicLong();
  private final ScheduledExecutorService executor;

  public SimGyroIO(GyroSimulation gyroSimulation) {
    this.gyroSimulation = gyroSimulation;
    this.lastGyro = new AtomicLong((Double.doubleToLongBits(0)));
    this.executor = Executors.newScheduledThreadPool(1);
  }

  public void start() {
    updateGyro();
    executor.scheduleAtFixedRate(
        () -> {
          if (shouldReset.get()) {
            gyroSimulation.setRotation(new Rotation2d(0));
            shouldReset.set(false);
          }
          if (shouldOffset.get()) {
            // TODO: Doesn't do anything for now, but metho is never used
            shouldOffset.set(false);
          }
          updateGyro();
        },
        0,
        Constants.GYRO_THREAD_RATE_MS,
        TimeUnit.MILLISECONDS);
  }

  @Override
  public void updateInputs(GyroInputs inputs) {
    inputs.connected = true;
    inputs.anglesInDeg = gyroSimulation.getGyroReading().getDegrees();
    inputs.angularVelocityInDeg =
        Units.degreesToRadians(gyroSimulation.getMeasuredAngularVelocity().in(DegreesPerSecond));
    inputs.odometryYawTimestamps = SparkUtil.getSimulationOdometryTimeStamps();
    inputs.odometryYawPositions = gyroSimulation.getCachedGyroReadings();
  }

  private void updateGyro() {
    lastGyro.set(
        Double.doubleToLongBits(((gyroSimulation.getGyroReading().getDegrees()) % 360) * -1));
  }

  public double getGyroValue() {
    return Double.longBitsToDouble(lastGyro.get());
  }

  public void resetGyro() {
    shouldReset.set(true);
  }

  public void setAngleOffset(double degrees) {
    gyroOffset.set(Double.doubleToLongBits(degrees));
    shouldOffset.set(true);
  }

  public double getAngleOffset() {
    return Double.longBitsToDouble(gyroOffset.get());
  }
}
