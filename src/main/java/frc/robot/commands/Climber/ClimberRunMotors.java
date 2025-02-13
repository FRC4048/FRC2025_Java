// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;

public class ClimberRunMotors extends Command {
  private final ClimberSubsystem climberSubsystem;
  private final double speed;
  private final Timer timer;

  /** Creates a new ClimberRunMotors. */
  public ClimberRunMotors(ClimberSubsystem climberSubsystem, double speed) {
    this.climberSubsystem = climberSubsystem;
    this.speed = speed;
    timer = new Timer();
    addRequirements(climberSubsystem);
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    climberSubsystem.setClimberSpeed(speed);
  }

  @Override
  public void end(boolean interrupted) {
    climberSubsystem.stopClimber();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.CLIMBER_RUN_MOTOR_TIMEOUT);
  }
}
