package frc.robot.subsystems.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.constants.Constants;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.ironmaple.simulation.drivesims.GyroSimulation;

public class SimThreadedGyro implements ThreadedGyroIO {
  private final GyroSimulation gyroSimulation;
  private final AtomicBoolean shouldReset = new AtomicBoolean(false);
  private final AtomicBoolean shouldOffset = new AtomicBoolean(false);
  private final AtomicLong lastGyro;
  private final AtomicLong gyroOffset = new AtomicLong();
  private final ScheduledExecutorService executor;

  public SimThreadedGyro(GyroSimulation gyroSimulation) {
    this.gyroSimulation = gyroSimulation;
    this.lastGyro = new AtomicLong((Double.doubleToLongBits(0)));
    this.executor = Executors.newScheduledThreadPool(1);
  }

  @Override
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
  public void stop() {
    executor.shutdownNow();
  }

  @Override
  public boolean stopAndWait(long maxTime, TimeUnit timeUnit) {
    executor.shutdownNow();
    try {
      return executor.awaitTermination(maxTime, timeUnit);
    } catch (InterruptedException e) {
      DriverStation.reportError(
          "ThreadedGyro thread termination was interrupted: " + e.getMessage(), true);
      return false;
    }
  }

  @Override
  public void updateGyro() {
    lastGyro.set(
        Double.doubleToLongBits(((gyroSimulation.getGyroReading().getDegrees()) % 360) * -1));
  }

  @Override
  public double getGyroValue() {
    return Double.longBitsToDouble(lastGyro.get());
  }

  @Override
  public void resetGyro() {
    shouldReset.set(true);
  }

  @Override
  public void setAngleAdjustment(double degrees) {
    gyroOffset.set(Double.doubleToLongBits(degrees));
    shouldOffset.set(true);
  }

  @Override
  public double getAngleOffset() {
    return Double.longBitsToDouble(gyroOffset.get());
  }
}
