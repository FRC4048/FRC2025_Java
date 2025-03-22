package frc.robot.subsystems.gyro;

import com.studica.frc.AHRS;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N2;
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
  private final AtomicLong lastGyro;
  private final AtomicLong gyroOffset = new AtomicLong();
  private final ScheduledExecutorService executor;
  private Vector<N2> currentVelocityValue;
  private double currentVelValueX;
  private double currentVelValueY;

  public ThreadedGyro(AHRS gyro) {
    this.gyro = gyro;
    this.currentVelocityValue = VecBuilder.fill(0, 0);
    this.currentVelValueX = 0;
    this.currentVelValueY = 0;
    this.lastGyro = new AtomicLong((Double.doubleToLongBits(0)));
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
    lastGyro.set(Double.doubleToLongBits(((gyro.getAngle()) % 360) * -1));
    currentVelValueX = gyro.getVelocityX();
    currentVelValueY = gyro.getVelocityY();
    currentVelocityValue = VecBuilder.fill(currentVelValueX, currentVelValueY);
  }

  public double getGyroValue() {
    return Double.longBitsToDouble(lastGyro.get());
  }

  public Vector<N2> getVelocityValue() {
    return currentVelocityValue;
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
}
