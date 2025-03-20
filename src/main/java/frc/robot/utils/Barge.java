// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/** Add your docs here. */
public enum Barge {
  BLUE_RIGHT_LOWER(8.9246, 0),
  BLUE_RIGHT_HIGHER(8.9246, 4),
  BLUE_LEFT_LOWER(7.56, 0),
  BLUEL_LEFT_HIGHER(7.56, 4);

  private final double x;
  private final double y;

  Barge(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getY() {
    return y;
  }

  public double getX() {
    return x;
  }
  public double getXBasedOnAlliance(DriverStation.Alliance AllianceColor) {
    return AllianceColor.equals(Alliance.Blue) ? x : x+4;
  }
}
