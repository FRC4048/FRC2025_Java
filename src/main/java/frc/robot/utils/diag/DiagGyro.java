package frc.robot.utils.diag;

import frc.robot.subsystems.gyro.ThreadedGyroIO;

public class DiagGyro extends DiagDistanceTraveled {
  private final ThreadedGyroIO gyro;

  public DiagGyro(String title, String name, double requiredTravel, ThreadedGyroIO gyro) {
    super(title, name, requiredTravel);
    this.gyro = gyro;
  }

  @Override
  protected double getCurrentValue() {
    return gyro.getGyroValue();
  }
}
