package frc.robot.constants;

public enum ElevatorPosition {
  CORAL_INTAKE(-1, 0),
  CLIMB(-5.2, 0),
  LEVEL1(-3.0, 1),
  LEVEL2(-9.28, 2),
  LEVEL3(-24, 3),
  LEVEL4(-47.4, 4);

  private final double heightElevator;
  private final int weight;

  ElevatorPosition(double heightElevator, int wight) {
    this.heightElevator = heightElevator;
    this.weight = wight;
  }

  public int getWeight() {
    return weight;
  }

  public double getElevatorHeight() {

    return heightElevator;
  }
}
