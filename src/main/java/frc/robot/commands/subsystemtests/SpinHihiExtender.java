// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subsystemtests;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SpinHihiExtender extends LoggableCommand {
  /** Creates a new SpinHihiExtender. */
  private final HihiExtenderSubsystem extender;

  private final double speedMotors;
  private final Timer timer;

  public SpinHihiExtender(HihiExtenderSubsystem extender, double speedMotors) {
    this.speedMotors = speedMotors;
    this.extender = extender;
    timer = new Timer();
    addRequirements(extender);
  }

  @Override
  public void initialize() {
    extender.setExtenderSpeed(speedMotors);
    timer.restart();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    extender.stopExtenderMotors();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.HIHI_EXTEND_TIMEOUT);
  }
}
