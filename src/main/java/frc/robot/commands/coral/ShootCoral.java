// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.CoralShooter.CoralShooterSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ShootCoral extends LoggableCommand {
  /** Creates a new ShootCoral. */
  private final CoralShooterSubsystem shooter;

  private final double speedMotors;
  private final Timer timer;

  public ShootCoral(CoralShooterSubsystem shooter, double speedMotors) {
    this.speedMotors = speedMotors;
    this.timer = new Timer();
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setShooterSpeed(speedMotors);
    timer.restart();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shooter.stopShooterMotors();
  }

  @Override
  public boolean isFinished() {
    return (timer.hasElapsed(Constants.SHOOT_CORAL_TIMEOUT));
  }
}
