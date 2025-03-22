// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.constants.Constants;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

public class RobotCentricDrive extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final DoubleSupplier joystickHorizontal;
  private final DoubleSupplier joystickVerticle;

  public RobotCentricDrive(
      SwerveDrivetrain drivetrain,
      DoubleSupplier joystickHorizontal,
      DoubleSupplier joystickVerticle) {
    this.drivetrain = drivetrain;
    this.joystickHorizontal = joystickHorizontal;
    this.joystickVerticle = joystickVerticle;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double y =
        MathUtil.applyDeadband(joystickHorizontal.getAsDouble(), 0.05) * Constants.MAX_VELOCITY;
    double x =
        MathUtil.applyDeadband(joystickVerticle.getAsDouble(), 0.05) * Constants.MAX_VELOCITY;
    ChassisSpeeds speeds = drivetrain.createChassisSpeeds(x, y, 0, DriveMode.ROBOT_CENTRIC);
    drivetrain.drive(speeds);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
