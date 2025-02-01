// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subsystemTests;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiRoller.HiHiRollerSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class SpinRoller extends LoggableCommand {
  /** Creates a new SpinRoller. */
  private final HiHiRollerSubsystem roller;

  private final double speedMotors;
  private Timer timer;

  public SpinRoller(HiHiRollerSubsystem roller, double speedMotors) {
    this.speedMotors = speedMotors;
    this.roller = roller;
    timer = new Timer();
    addRequirements(roller);
  }

  @Override
  public void initialize() {
    roller.setRollerMotorSpeed(speedMotors);
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    roller.stopHihiRollerMotor();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.HIHI_ROLLER_TIMEOUT);
  }
}
