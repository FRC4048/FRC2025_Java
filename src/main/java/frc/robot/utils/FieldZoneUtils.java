// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Robot;
import java.util.Optional;

/** Add your docs here. */
public class FieldZoneUtils {

  public static boolean isInOppositeBarge(double x, double y) {
    Optional<DriverStation.Alliance> al = Robot.getAllianceColor();
    if ((x < Barge.RIGHT_HIGHER.getX()) && (x > Barge.LEFT_LOWER.getX()) && (al.isPresent())) {
      return al.get().equals(Alliance.Red)
          ? (y < Barge.RIGHT_HIGHER.getY())
          : (y > Barge.RIGHT_HIGHER.getY());
    }
    return false;
  }
}
