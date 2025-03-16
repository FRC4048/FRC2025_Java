package frc.robot.subsystems.gyro;

import java.util.concurrent.TimeUnit;

public interface ThreadedGyroIO {
  void start();

  void stop();

  boolean stopAndWait(long maxTime, TimeUnit timeUnit);

  void updateGyro();

  double getGyroValue();

  void resetGyro();

  void setAngleAdjustment(double degrees);

  double getAngleOffset();
}
