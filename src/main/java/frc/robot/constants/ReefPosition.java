package frc.robot.constants;

public enum ReefPosition {
  LEVEL0(-1),
  LEVEL1(-10),
  LEVEL2(-20),
  LEVEL3(-30),
  LEVEL4(-45);

  private final double heightElevator;

  ReefPosition(double heightElevator) {
    this.heightElevator = heightElevator;
  }

  public double getElevatorHeight() {
    return heightElevator;
  }
}
