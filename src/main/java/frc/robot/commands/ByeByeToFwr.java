// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.AlgaeByeByeTilt.AlgaeByeByeTiltSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ByeByeToFwr extends Command {
  /** Creates a new byeByeGoToAngle. */
  private final AlgaeByeByeTiltSubsystem tiltMotor;
  private double startTime;

  public ByeByeToFwr(AlgaeByeByeTiltSubsystem tiltMotor) {
    this.tiltMotor = tiltMotor;
    addRequirements(tiltMotor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tiltMotor.setSpeed(Constants.BYEBYE_TILT_SPEED);
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    tiltMotor.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (tiltMotor.getForwardSwitchState()
        || Timer.getFPGATimestamp() - startTime >= Constants.BYEBYE_TILT_TIMEOUT);
  }
}
