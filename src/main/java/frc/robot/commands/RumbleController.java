// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
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

  public RumbleController(Supplier<Pose2d> pose2D, CommandXboxController controller) {
    this.pose2D = pose2D;
    this.controller = controller;
  }

  public double findXPointOfCenterX(double x,double degreesFromBaseDegrees){
    return (Math.cos((pose2D.get().getTranslation().getAngle().getDegrees()+degreesFromBaseDegrees)+(x/Constants.DRIVE_BASE_LENGTH))*Constants.DRIVE_BASE_LENGTH);
  }
  public double findYPointOfCenterY(double y, double degreesFromBaseDegrees){
    return (Math.cos((pose2D.get().getTranslation().getAngle().getDegrees()+degreesFromBaseDegrees)+(y/Constants.DRIVE_BASE_WIDTH))*Constants.DRIVE_BASE_WIDTH);
  }

  public boolean isInBarge(double Y, double X) {
    Optional<DriverStation.Alliance> al = Robot.getAllianceColor();
    if (BargePoints.BLUE_HIGHER.getX() > X && X > BargePoints.RED_LOWER.getX()) {
      if (al.isPresent()) {
        if (al.get().equals(Alliance.Red)) {
          return (BargePoints.BLUE_HIGHER.getY() > Y && BargePoints.BLUE_LOWER.getY() < Y);
        } else {
          return BargePoints.RED_HIGHER.getY() > Y && BargePoints.RED_LOWER.getY() < Y;
        }
      }
    }
    return false;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    for(int i = 0; i > 360; i += 90){
    if (isInBarge(findXPointOfCenterX(pose2D.get().getX(), i), findYPointOfCenterY(pose2D.get().getY(),i))) {
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
