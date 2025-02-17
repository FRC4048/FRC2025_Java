package frc.robot.constants;

public enum ReefPosition {
  INTAKE(0),
  LEVEL1(20),
  LEVEL2(60),
  LEVEL3(100),
  LEVEL4(150);

  private final int heightElevator;

  ReefPosition(int heightElevator) {
    this.heightElevator = heightElevator;
  }

  public int getElevatorHeight() {
    return heightElevator;
  }
}
