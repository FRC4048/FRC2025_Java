// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subsystemTests;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.AlgaeByeByeTilt.AlgaeByeByeTiltSubsystem;
import frc.robot.utils.logging.LoggableCommand;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SpinTilt extends LoggableCommand {
  /** Creates a new SpinTIlt. */
  private final Timer timer;

  private final double motorSpeed;
  private final AlgaeByeByeTiltSubsystem tilt;

  public SpinTIlt(AlgaeByeByeTiltSubsystem tilt, double motorSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.motorSpeed = motorSpeed;
    this.tilt = tilt;
    timer = new Timer();
    addRequirements(tilt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tilt.setSpeed(motorSpeed);
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    tilt.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.BYEBYE_TILT_TIMEOUT);
  }
}
