package frc.robot.constants;

public enum ReefPosition {
  LEVEL0(0),
  LEVEL1(20),
  LEVEL2(60),
  LEVEL3(100),
  LEVEL4(Constants.MAX_ELEVATOR_HEIGHT_METERS);

  private final double heightElevator;

  ReefPosition(double heightElevator) {
    this.heightElevator = heightElevator;
  }

  public double getElevatorHeight() {
    return heightElevator;
  }
}
