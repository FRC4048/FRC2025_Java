// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

/** Add your docs here. */
public enum BargePoints {
  BLUE_BARGE_ENTRANCE(7.56, 4),
  RED_BARGE_ENTRANCE(8.9246, 4);

  private final double x;
  private final double y;

  BargePoints(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getY() {
    return y;
  }

  public double getX() {
    return x;
  }
}
