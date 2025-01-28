package frc.robot.constants;

public enum CoralDeposit {
  INTAKE(0, 0),
  LEVEL1(20, 10),
  LEVEL2(60, 35),
  LEVEL3(100, 40);

  private final int height;
  private final int angle;

  CoralDeposit(int height, int angle) {
    this.angle = angle;
    this.height = height;
  }

  public int getAngle() {
    return angle;
  }

  public int getHeight() {
    return height;
  }
}
