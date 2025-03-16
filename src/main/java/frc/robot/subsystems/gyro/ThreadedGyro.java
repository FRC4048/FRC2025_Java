package frc.robot.subsystems.gyro;

import com.studica.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.constants.Constants;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadedGyro {
  private final AHRS gyro;
  private final AtomicBoolean shouldReset = new AtomicBoolean(false);
  private final AtomicBoolean shouldOffset = new AtomicBoolean(false);
  private final AtomicLong lastGyroAngle;
  private final AtomicLong lastGyroAccelerationX;
  private final AtomicLong lastGyroAccelerationY;
  private final AtomicLong lastGyroAccelerationZ;

  private final AtomicLong lastGyroVelX;
  private final AtomicLong lastGyroVelY;
  private final AtomicLong lastGyroVelZ;
  private final AtomicLong gyroOffset = new AtomicLong();
  private final ScheduledExecutorService executor;

  public ThreadedGyro(AHRS gyro) {
    this.gyro = gyro;
    this.lastGyroAngle = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastGyroAccelerationX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastGyroAccelerationY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastGyroAccelerationZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastGyroVelX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastGyroVelY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastGyroVelZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.executor = Executors.newScheduledThreadPool(1);
  }

  public void start() {
    updateGyro();
    executor.scheduleAtFixedRate(
        () -> {
          if (shouldReset.get()) {
            gyro.reset();
            shouldReset.set(false);
          }
          if (shouldOffset.get()) {
            gyro.setAngleAdjustment(Double.longBitsToDouble(gyroOffset.get()));
            shouldOffset.set(false);
          }
          updateGyro();
        },
        0,
        Constants.GYRO_THREAD_RATE_MS,
        TimeUnit.MILLISECONDS);
  }

  public void stop() {
    executor.shutdownNow();
  }

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

  private void updateGyro() {
    lastGyroAngle.set(Double.doubleToLongBits(((gyro.getAngle()) % 360) * -1));
    lastGyroAccelerationX.set(Double.doubleToLongBits(gyro.getWorldLinearAccelX()));
    lastGyroAccelerationY.set(Double.doubleToLongBits(gyro.getWorldLinearAccelY()));
    lastGyroAccelerationZ.set(Double.doubleToLongBits(gyro.getWorldLinearAccelZ()));

    lastGyroVelX.set(Double.doubleToLongBits(gyro.getVelocityX()));
    lastGyroVelY.set(Double.doubleToLongBits(gyro.getVelocityY()));
    lastGyroVelZ.set(Double.doubleToLongBits(gyro.getVelocityZ()));
  }

  public double getGyroValue() {
    return Double.longBitsToDouble(lastGyroAngle.get());
  }

  public void resetGyro() {
    shouldReset.set(true);
  }

  public void setAngleAdjustment(double degrees) {
    gyroOffset.set(Double.doubleToLongBits(degrees));
    shouldOffset.set(true);
  }

  public double getAngleOffset() {
    return Double.longBitsToDouble(gyroOffset.get());
  }

  public double getAccelX() {
    return Double.longBitsToDouble(lastGyroAccelerationX.get());
  }

  public double getAccelY() {
    return Double.longBitsToDouble(lastGyroAccelerationY.get());
  }

  public double getAccelZ() {
    return Double.longBitsToDouble(lastGyroAccelerationZ.get());
  }

  public double getVelX() {
    return Double.longBitsToDouble(lastGyroVelX.get());
  }

  public double getVelY() {
    return Double.longBitsToDouble(lastGyroVelY.get());
  }

  public double getVelZ() {
    return Double.longBitsToDouble(lastGyroVelZ.get());
  }
}
