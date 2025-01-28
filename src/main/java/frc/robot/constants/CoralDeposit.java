package frc.robot.constants;

public enum CoralDeposit {
  INTAKE(0, 0),
  MIDDLE(20, 10),
  HIGH(60, 35),
  TOP(100, 40);

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
