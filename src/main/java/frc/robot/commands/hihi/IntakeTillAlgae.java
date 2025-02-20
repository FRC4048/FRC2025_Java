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
  private double currentVelocity;
  private int validTicks;

  public IntakeTillAlgae(HihiRollerSubsystem hihiRoller) {
    this.hihiRoller = hihiRoller;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("Intake Till Algae");
    addRequirements(hihiRoller);
  }

  @Override
  public void initialize() {
    timer.reset();
    currentVelocity = 0;
    hihiRoller.setRollerMotorSpeed(Constants.HIHI_INTAKE_SPEED);
    validTicks = 0;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    hihiRoller.stopHihiRollerMotor();
  }

  @Override
  public boolean isFinished() {
    if (currentVelocity == 0) currentVelocity = hihiRoller.getRollerVelocity();
    if (timer.hasElapsed(Constants.HIHI_INTAKE_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    if (currentVelocity > hihiRoller.getRollerVelocity()) {
      currentVelocity = hihiRoller.getRollerVelocity();
      validTicks++;
      if (validTicks > Constants.HIHI_ROLLER_MAX_TICKS)
        return currentVelocity < Constants.HIHI_INTAKE_MIN_SPEED;
    }
    currentVelocity = hihiRoller.getRollerVelocity();
    return false;
  }
}
