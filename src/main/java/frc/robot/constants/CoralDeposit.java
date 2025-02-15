package frc.robot.constants;

public enum CoralDeposit {
  INTAKE(0, 0),
  LEVEL1(20, 10),
  LEVEL2(60, 35),
  LEVEL3(100, 40),
  LEVEL4(150, 60);

  private final int heightElevator;
  private final int angleElevator;

  CoralDeposit(int heightElevator, int angleElevator) {
    this.angleElevator = angleElevator;
    this.heightElevator = heightElevator;
  }

  public int getElevatorAngle() {
    return angleElevator;
  }

  public int getElevatorHeight() {
    return heightElevator;
  }
}
