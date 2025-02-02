// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class ShootCoral extends LoggableCommand {
  /** Creates a new ShootCoral. */
  private final CoralSubsystem shooter;

  private final double speedMotors;
  private double startTime;

  public ShootCoral(CoralSubsystem shooter, double speedMotors) {
    this.speedMotors = speedMotors;
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setShooterSpeed(speedMotors);
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shooter.stopShooterMotors();
  }

  @Override
  public boolean isFinished() {
      return (Timer.getFPGATimestamp() - startTime >= Constants.SHOOT_CORAL_TIMEOUT);
  }
}
