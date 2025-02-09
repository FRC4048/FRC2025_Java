// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subsystemtests;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SpinRollerByeBye extends LoggableCommand {
  /** Creates a new ByeByeSpinExtender. */
  private final Timer timer;

  private final double motorSpeed;
  private final AlgaeByeByeRollerSubsystem roller;

  public SpinRollerByeBye(AlgaeByeByeRollerSubsystem roller, double motorSpeed) {
    this.motorSpeed = motorSpeed;
    this.roller = roller;
    timer = new Timer();
    addRequirements(roller);
  }

  @Override
  public void initialize() {
    timer.restart();
    roller.setSpeed(motorSpeed);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    roller.stopMotors();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.BYEBYE_SPIN_ROLLER_TIMEOUT);
  }
}
