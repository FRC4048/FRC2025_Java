// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.Supplier;

public class ShootCoral extends LoggableCommand {
  /** Creates a new ShootCoral. */
  private final CoralSubsystem shooter;

  private final Supplier<ElevatorPosition> storedPosition;
  private final Timer timer;

  public ShootCoral(CoralSubsystem shooter, Supplier<ElevatorPosition> storedPosition) {
    this.timer = new Timer();
    this.shooter = shooter;
    this.storedPosition = storedPosition;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setLimitSwitchState(false);
    shooter.setShooterSpeed(storedPosition.get().getShootSpeed());
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
