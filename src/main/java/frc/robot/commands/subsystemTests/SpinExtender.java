// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subsystemTests;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiExtender.HiHiExtenderSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class SpinExtender extends LoggableCommand {
  /** Creates a new SpinExtender. */
  private final HiHiExtenderSubsystem extender;

  private final double speedMotors;
  private Timer timer;

  public SpinExtender(HiHiExtenderSubsystem extender, double speedMotors) {
    this.speedMotors = speedMotors;
    this.extender = extender;
    timer = new Timer();
    addRequirements(extender);
  }

  @Override
  public void initialize() {
    extender.setExtenderSpeed(speedMotors);
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    extender.stopExtenderMotors();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.HIHI_EXTENDER_TIMEOUT);
  }
}
