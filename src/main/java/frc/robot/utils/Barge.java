// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Robot;

/** Add your docs here. */
public enum Barge {
  RIGHT_LOWER(8.9246, 0),
  RIGHT_HIGHER(8.9246, 4),
  LEFT_LOWER(7.56, 0),
  LEFT_HIGHER(7.56, 4);

  private final double x;
  private final double y;

  Barge(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY(DriverStation.Alliance AllianceColor) {
    return AllianceColor.equals(Alliance.Blue) ? y : y + 4;
  }
  public double getYFromAlliaceColor() {
    return Robot.getAllianceColor().get().equals(Alliance.Blue) ? y : y + 4;
  }
}
