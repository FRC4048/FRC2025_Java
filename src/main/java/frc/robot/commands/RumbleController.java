// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.BargePoints;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.Optional;
import java.util.function.Supplier;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class RumbleController extends LoggableCommand {

  private final Supplier<Pose2d> pose2D;
  private final CommandXboxController controller;
  private final Supplier<ChassisSpeeds> speeds;

  public RumbleController(
      Supplier<Pose2d> pose2D, CommandXboxController controller, Supplier<ChassisSpeeds> speeds) {
    this.pose2D = pose2D;
    this.controller = controller;
    this.speeds = speeds;
  }

  public static double findXPointOfCenterX(double x, double degreesFromBaseDegrees, Pose2d pose2D) {
    return (Math.cos(
            (pose2D.getTranslation().getAngle().getDegrees() + degreesFromBaseDegrees)
                * Constants.DRIVE_BASE_LENGTH)
        + x);
  }

  public static double findYPointOfCenterY(double y, double degreesFromBaseDegrees, Pose2d pose2D) {
    return (Math.cos(
            (pose2D.getTranslation().getAngle().getDegrees() + degreesFromBaseDegrees)
                * Constants.DRIVE_BASE_WIDTH)
        + y);
  }

  public static boolean isInOpponentsBarge(double y, double x) {
    Optional<DriverStation.Alliance> al = Robot.getAllianceColor();
    if (BargePoints.BLUE_BARGE_ENTRANCE.getX() < x && x < BargePoints.RED_BARGE_ENTRANCE.getX()) {
      if (al.isPresent()) {
        if (al.get().equals(Alliance.Red)) {
          return (BargePoints.BLUE_BARGE_ENTRANCE.getY() < y);
        } else {
          return BargePoints.RED_BARGE_ENTRANCE.getY() > y;
        }
      }
    }
    return false;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    for (int i = 45; i > 360; i += 90) {
      if (isInOpponentsBarge(
          findXPointOfCenterX(
              speeds.get().vxMetersPerSecond * Constants.TIME_UNTILL_IN_BARGE, i, pose2D.get()),
          findYPointOfCenterY(
              speeds.get().vyMetersPerSecond * Constants.TIME_UNTILL_IN_BARGE, i, pose2D.get()))) {
        controller.setRumble(RumbleType.kBothRumble, 2);
        break;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
