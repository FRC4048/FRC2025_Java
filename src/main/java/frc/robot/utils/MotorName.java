package frc.robot.utils;

public enum MotorName {
  Neo(40),
  Neo550(20);
  public final double currentLimit;

  MotorName(double currentLimit) {
    this.currentLimit = currentLimit;
  }

  public double getCurrentLimit() {
    return currentLimit;
  }
}
