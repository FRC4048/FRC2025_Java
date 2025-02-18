package frc.robot.constants;

public enum ReefPosition {
  LEVEL0(0), //Starting and Intake Height
  LEVEL1(20), //L1 Scoring
  LEVEL2(60), //L2 Scoring and Lower Algae ByeBye
  LEVEL3(100),//L3 Scoring and Higher Algae ByeBye
  LEVEL4(200);//L4 Scoring

  private final double heightElevator;

  ReefPosition(double heightElevator) {
    this.heightElevator = heightElevator;
  }

  public double getElevatorHeight() {
    return heightElevator;
  }
}
