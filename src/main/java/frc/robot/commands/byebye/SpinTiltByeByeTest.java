// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.byebye;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SpinTiltByeByeTest extends LoggableCommand {
  /** Creates a new SpinTIlt. */
  private final Timer timer;

  private final double motorSpeed;
  private final AlgaeByeByeTiltSubsystem tilt;

  public SpinTiltByeByeTest(AlgaeByeByeTiltSubsystem tilt, double motorSpeed) {
    this.motorSpeed = motorSpeed;
    this.tilt = tilt;
    timer = new Timer();
    addRequirements(tilt);
  }

  @Override
  public void initialize() {
    timer.restart();
    tilt.setSpeed(motorSpeed);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    tilt.stopMotors();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.BYEBYE_SPIN_TILT_TIMEOUT);
  }
}
