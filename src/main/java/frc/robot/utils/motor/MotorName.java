package frc.robot.utils.motor;

public enum MotorName {
  Neo(40),
  Neo550(20);
  public final int currentLimit;

  MotorName(int currentLimit) {
    this.currentLimit = currentLimit;
  }

  public int getCurrentLimit() {
    return currentLimit;
  }
}
