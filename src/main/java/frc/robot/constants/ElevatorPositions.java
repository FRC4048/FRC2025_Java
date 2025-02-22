package frc.robot.constants;

public enum ElevatorPositions {
  CORAL_INTAKE(-1),
  LEVEL1(-10),
  LEVEL2(-20),
  LEVEL3(-30),
  LEVEL4(-47);

  private final double heightElevator;

  ElevatorPositions(double heightElevator) {
    this.heightElevator = heightElevator;
  }

  public double getElevatorHeight() {
    return heightElevator;
  }
}
