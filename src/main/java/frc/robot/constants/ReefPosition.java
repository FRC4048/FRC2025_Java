package frc.robot.constants;

public enum ReefPosition {
  LEVEL0(0),
  LEVEL1(20),
  LEVEL2(60),
  LEVEL3(100),
  LEVEL4(200);

  private final double heightElevator;

  ReefPosition(double heightElevator) {
    this.heightElevator = heightElevator;
  }

  public double getElevatorHeight() {
    return heightElevator;
  }
}
