package frc.robot.utils.logging.subsystem.providers;

import edu.wpi.first.math.geometry.Rotation2d;

public interface SteerMotorInputProvider extends MotorInputProvider {
  boolean isSteerConnected();

  double[] getOdometryTimestamps();

  Rotation2d[] getOdometryTurnPositions();
}
