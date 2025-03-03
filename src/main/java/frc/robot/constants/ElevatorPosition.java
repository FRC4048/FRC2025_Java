package frc.robot.constants;

public enum ElevatorPosition {
  CORAL_INTAKE(-1, 0.0), // shoot speed for intake is not used
  LEVEL1(-3.0, .8),
  LEVEL2(-9.0, .8),
  LEVEL3(-24, .8),
  LEVEL4(-47, .6);

  private final double heightElevator;
  private final double shootSpeed;

  ElevatorPosition(double heightElevator, double shootSpeed) {
    this.heightElevator = heightElevator;
    this.shootSpeed = shootSpeed;
  }

  public double getElevatorHeight() {
    return heightElevator;
  }

  public double getShootSpeed() {
    return shootSpeed;
  }
}
