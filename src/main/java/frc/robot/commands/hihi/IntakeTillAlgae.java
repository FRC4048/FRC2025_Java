// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class IntakeTillAlgae extends LoggableCommand {
  private final HihiRollerSubsystem hihiRoller;
  private final Timer timer;
  private final TimeoutLogger timeoutCounter;
  private double lastVelocity;
  private double validTicks;

  public IntakeTillAlgae(HihiRollerSubsystem hihiRoller) {
    this.hihiRoller = hihiRoller;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("Intake Hi Hi Till Algae");
  }

  @Override
  public void initialize() {
    timer.restart();
    lastVelocity = hihiRoller.getRollerEncoderVelocity();
    validTicks = 0;
    hihiRoller.setRollerMotorSpeed(Constants.HIHI_INTAKE_SPEED);
  }

  @Override
  public void execute() {
    validTicks = lastVelocity > hihiRoller.getRollerEncoderVelocity() ? validTicks + 1 : validTicks;
    lastVelocity = hihiRoller.getRollerEncoderVelocity();
  }

  @Override
  public void end(boolean interrupted) {
    hihiRoller.stopHihiRollerMotor();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.HIHI_INTAKE_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return ((validTicks > Constants.MAX_VALID_TICKS_INTAKE)
        && lastVelocity < Constants.MIN_HIHI_INTAKE_VELOCITY);
  }
}
