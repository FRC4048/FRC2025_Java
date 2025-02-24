package frc.robot.constants;

public enum ElevatorPosition {
  CORAL_INTAKE(-1),
  LEVEL1(-3.0),
  LEVEL2(-9.0),
  LEVEL3(-24),
  LEVEL4(-47);

  private final double heightElevator;

  ElevatorPosition(double heightElevator) {
    this.heightElevator = heightElevator;
  }

  public double getElevatorHeight() {
    return heightElevator;
  }
}
