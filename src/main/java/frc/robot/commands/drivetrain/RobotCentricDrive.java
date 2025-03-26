// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.DriveMode;
import frc.robot.utils.logging.commands.LoggableCommand;

public class RobotCentricDrive extends LoggableCommand {
  private final SwerveDrivetrain drivetrain;
  private final double xMove;
  private final double time;
  private final Timer timer;

  public RobotCentricDrive(SwerveDrivetrain drivetrain, double xMove, double time) {
    this.drivetrain = drivetrain;
    this.xMove = xMove;
    this.time = time;
    timer = new Timer();
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    ChassisSpeeds speeds = drivetrain.createChassisSpeeds(xMove, 0, 0, DriveMode.ROBOT_CENTRIC);
    drivetrain.drive(speeds);
  }

  @Override
  public void end(boolean interrupted) {
    ChassisSpeeds speeds = drivetrain.createChassisSpeeds(0, 0, 0, DriveMode.FIELD_CENTRIC);
    drivetrain.drive(speeds);
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(time);
  }
}
