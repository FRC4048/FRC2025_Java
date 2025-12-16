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
  private final AtomicLong lastGyro;
  private final AtomicLong lastBlah1;
  private final AtomicLong lastWorldLinearAccelX;
  private final AtomicLong lastWorldLinearAccelY;
  private final AtomicLong lastWorldLinearAccelZ;
  private final AtomicLong lastVelocityX;
  private final AtomicLong lastVelocityY;
  private final AtomicLong lastVelocityZ;
  private final AtomicLong lastDisplacementX;
  private final AtomicLong lastDisplacementY;
  private final AtomicLong lastDisplacementZ;
  private final AtomicLong lastRawGyroX;
  private final AtomicLong lastRawGyroY;
  private final AtomicLong lastRawGyroZ;
  private final AtomicLong lastRawAccelX;
  private final AtomicLong lastRawAccelY;
  private final AtomicLong lastRawAccelZ;
  private final AtomicLong lastRawMagX;
  private final AtomicLong lastRawMagY;
  private final AtomicLong lastRawMagZ;
  private final AtomicLong lastYaw;
  private final AtomicLong lastPitch;
  private final AtomicLong lastRoll;
  private final AtomicLong lastFusedHeading;
  private final AtomicLong lastCompassHeading;
  private final AtomicLong lastRobotCentricVelocityX;
  private final AtomicLong lastRobotCentricVelocityY;
  private final AtomicLong lastRobotCentricVelocityZ;

  private final AtomicLong gyroOffset = new AtomicLong();
  private final ScheduledExecutorService executor;

  public ThreadedGyro(AHRS gyro) {
    this.gyro = gyro;
    this.lastGyro = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastBlah1 = new AtomicLong(Double.doubleToLongBits(0));
    this.lastWorldLinearAccelX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastWorldLinearAccelY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastWorldLinearAccelZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastVelocityX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastVelocityY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastVelocityZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastDisplacementX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastDisplacementY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastDisplacementZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawGyroX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawGyroY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawGyroZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawAccelX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawAccelY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawAccelZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawMagX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawMagY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRawMagZ = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastYaw = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastPitch = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRoll = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastCompassHeading = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastFusedHeading = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRobotCentricVelocityX = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRobotCentricVelocityY = new AtomicLong((Double.doubleToLongBits(0)));
    this.lastRobotCentricVelocityZ = new AtomicLong((Double.doubleToLongBits(0)));

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
    lastBlah1.set(Double.doubleToLongBits(gyro.getRawAccelX()));

    lastWorldLinearAccelX.set(Double.doubleToLongBits(gyro.getWorldLinearAccelX()));
    lastWorldLinearAccelY.set(Double.doubleToLongBits(gyro.getWorldLinearAccelY()));
    lastWorldLinearAccelZ.set(Double.doubleToLongBits(gyro.getWorldLinearAccelZ()));

    lastVelocityX.set(Double.doubleToLongBits(gyro.getVelocityX()));
    lastVelocityY.set(Double.doubleToLongBits(gyro.getVelocityY()));
    lastVelocityZ.set(Double.doubleToLongBits(gyro.getVelocityZ()));

    lastDisplacementX.set(Double.doubleToLongBits(gyro.getDisplacementX()));
    lastDisplacementY.set(Double.doubleToLongBits(gyro.getDisplacementY()));
    lastDisplacementZ.set(Double.doubleToLongBits(gyro.getDisplacementZ()));

    lastRawGyroX.set(Double.doubleToLongBits(gyro.getRawGyroX()));
    lastRawGyroY.set(Double.doubleToLongBits(gyro.getRawGyroY()));
    lastRawGyroZ.set(Double.doubleToLongBits(gyro.getRawGyroZ()));

    lastRawAccelX.set(Double.doubleToLongBits(gyro.getRawAccelX()));
    lastRawAccelY.set(Double.doubleToLongBits(gyro.getRawAccelY()));
    lastRawAccelZ.set(Double.doubleToLongBits(gyro.getRawAccelZ()));

    lastRawMagX.set(Double.doubleToLongBits(gyro.getRawMagX()));
    lastRawMagY.set(Double.doubleToLongBits(gyro.getRawMagY()));
    lastRawMagZ.set(Double.doubleToLongBits(gyro.getRawMagZ()));

    lastYaw.set(Double.doubleToLongBits(gyro.getYaw()));
    lastPitch.set(Double.doubleToLongBits(gyro.getPitch()));
    lastRoll.set(Double.doubleToLongBits(gyro.getRoll()));

    lastFusedHeading.set(Double.doubleToLongBits(gyro.getFusedHeading()));
    lastCompassHeading.set(Double.doubleToLongBits(gyro.getCompassHeading()));

    lastRobotCentricVelocityX.set(Double.doubleToLongBits(gyro.getRobotCentricVelocityX()));
    lastRobotCentricVelocityY.set(Double.doubleToLongBits(gyro.getRobotCentricVelocityY()));
    lastRobotCentricVelocityZ.set(Double.doubleToLongBits(gyro.getRobotCentricVelocityZ()));
  }

  public double getGyroValue() {
    return Double.longBitsToDouble(lastGyro.get());
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

  public double getBlah1() {
    return Double.longBitsToDouble(lastBlah1.get());
  }

  public double getWorldLinearAccelX() {
    return Double.longBitsToDouble(lastWorldLinearAccelX.get());
  }

  public double getWorldLinearAccelY() {
    return Double.longBitsToDouble(lastWorldLinearAccelY.get());
  }

  public double getWorldLinearAccelZ() {
    return Double.longBitsToDouble(lastWorldLinearAccelZ.get());
  }

  public double getVelocityX() {
    return Double.longBitsToDouble(lastVelocityX.get());
  }

  public double getVelocityY() {
    return Double.longBitsToDouble(lastVelocityY.get());
  }

  public double getVelocityZ() {
    return Double.longBitsToDouble(lastVelocityZ.get());
  }

  public double getDisplacementX() {
    return Double.longBitsToDouble(lastDisplacementX.get());
  }

  public double getDisplacementY() {
    return Double.longBitsToDouble(lastDisplacementY.get());
  }

  public double getDisplacementZ() {
    return Double.longBitsToDouble(lastDisplacementZ.get());
  }

  public double getRawGyroX() {
    return Double.longBitsToDouble(lastRawGyroX.get());
  }

  public double getRawGyroY() {
    return Double.longBitsToDouble(lastRawGyroY.get());
  }

  public double getRawGyroZ() {
    return Double.longBitsToDouble(lastRawGyroZ.get());
  }

  public double getRawAccelX() {
    return Double.longBitsToDouble(lastRawAccelX.get());
  }

  public double getRawAccelY() {
    return Double.longBitsToDouble(lastRawAccelY.get());
  }

  public double getRawAccelZ() {
    return Double.longBitsToDouble(lastRawAccelZ.get());
  }

  public double getRawMagX() {
    return Double.longBitsToDouble(lastRawMagX.get());
  }

  public double getRawMagY() {
    return Double.longBitsToDouble(lastRawMagY.get());
  }

  public double getRawMagZ() {
    return Double.longBitsToDouble(lastRawMagZ.get());
  }

  public double getYaw() {
    return Double.longBitsToDouble(lastYaw.get());
  }

  public double getPitch() {
    return Double.longBitsToDouble(lastPitch.get());
  }

  public double getRoll() {
    return Double.longBitsToDouble(lastRoll.get());
  }

  public double getFusedHeading() {
    return Double.longBitsToDouble(lastFusedHeading.get());
  }

  public double getCompassHeading() {
    return Double.longBitsToDouble(lastCompassHeading.get());
  }

  public double getRobotCentricVelocityX() {
    return Double.longBitsToDouble(lastRobotCentricVelocityX.get());
  }

  public double getRobotCentricVelocityY() {
    return Double.longBitsToDouble(lastRobotCentricVelocityY.get());
  }

  public double getRobotCentricVelocityZ() {
    return Double.longBitsToDouble(lastRobotCentricVelocityZ.get());
  }
}
