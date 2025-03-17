package frc.robot.utils;

public enum Barge {
  RED(7.56, 1.3646, 0, 4),
  BLUE(7.56, 1.3646, 4, 8);

  private final double x;
  private final double width;
  private final double y;
  private final double height;

  Barge(double x, double width, double y, double height) {
    this.x = x;
    this.width = width;
    this.y = y;
    this.height = height;
  }

  public double getX() {
    return x;
  }

  public double getWidth() {
    return width;
  }

  public double getY() {
    return y;
  }

  public double getHeight() {
    return height;
  }
}
