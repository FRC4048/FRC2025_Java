// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.climber.ClimberSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ClimberRunMotors extends Command {
  private final ClimberSubsystem climberSubsystem;
  private final double speed;
  private Timer timer;

  /** Creates a new ClimberRunMotors. */
  public ClimberRunMotors(ClimberSubsystem climberSubsystem, double speed) {
    this.climberSubsystem = climberSubsystem;
    this.speed = speed;
    timer = new Timer();
    addRequirements(climberSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.restart();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climberSubsystem.setClimberSpeed(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climberSubsystem.stopClimber();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.SHOOT_CORAL_TIMEOUT);
  }
}
