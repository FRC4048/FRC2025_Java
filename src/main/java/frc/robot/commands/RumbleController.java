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
import frc.robot.utils.Barge;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.Optional;
import java.util.function.Supplier;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class RumbleController extends LoggableCommand {

  private final Supplier<Pose2d> robotPose;
  private final CommandXboxController controller;

  public RumbleController(Supplier<Pose2d> robotPose, CommandXboxController controller) {
    this.robotPose = robotPose;
    this.controller = controller;
  }

  public static boolean isInOppositeBarge(double x, double y) {
    Optional<DriverStation.Alliance> al = Robot.getAllianceColor();
    if ((x < Barge.BLUE_RIGHT_HIGHER.getX())
        && (x > Barge.BLUE_LEFT_LOWER.getX())
        && (al.isPresent())) {
      return al.get().equals(Alliance.Red)
          ? (y < Barge.BLUE_RIGHT_HIGHER.getY(al.get()))
          : (y > Barge.BLUE_RIGHT_HIGHER.getY(al.get()));
    }
    return false;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (isInOppositeBarge(robotPose.get().getX(), robotPose.get().getY())) {
      if (Constants.RUMBLE_CONTROLLER) {
        controller.setRumble(RumbleType.kBothRumble, 1);
      }
    } else controller.setRumble(RumbleType.kBothRumble, 0);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
