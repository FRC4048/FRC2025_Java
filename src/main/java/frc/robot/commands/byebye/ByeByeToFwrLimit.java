// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.byebye;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ByeByeToFwrLimit extends LoggableCommand {

  private final AlgaeByeByeTiltSubsystem tiltMotor;

  private final TimeoutLogger timeoutCounter;
  private final Timer timer;

  public ByeByeToFwrLimit(AlgaeByeByeTiltSubsystem tiltMotor) {
    this.tiltMotor = tiltMotor;
    this.timer = new Timer();
    timeoutCounter = new TimeoutLogger("ByeBye to fwr limit");
    addRequirements(tiltMotor);
  }

  @Override
  public void initialize() {
    timer.restart();

    tiltMotor.setSpeed(Constants.BYEBYE_FORWARD_SPEED);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    tiltMotor.stopMotors();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.BYEBYE_FORWARD_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return (tiltMotor.getForwardSwitchState());
  }
}
