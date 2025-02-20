// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class RollAlgae extends Command {
  /** Creates a new AlgaeRoller. */
  private final HihiRollerSubsystem roller;

  private final Timer timer;
  private final double speed;

  public RollAlgae(HihiRollerSubsystem roller, double speed) {
    this.timer = new Timer();
    this.speed = speed;
    this.roller = roller;
    addRequirements(roller);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.restart();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    roller.setRollerMotorSpeed(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    roller.stopHihiRollerMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.ROLL_ALGAE_TIMEOUT);
  }
}
