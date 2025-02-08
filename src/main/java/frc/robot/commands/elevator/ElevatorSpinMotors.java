// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.LoggableCommand;
import frc.robot.utils.logging.TimeoutLogger;

public class ElevatorSpinMotors extends LoggableCommand {
  /** Creates a new ElevatorSpinMotors. */
  private final ElevatorSubsystem elevator;

  private final TimeoutLogger timeoutCounter;
  public final Timer timer;

  public ElevatorSpinMotors(ElevatorSubsystem elevator) {
    this.elevator = elevator;
    this.timer = new Timer();
    timeoutCounter = new TimeoutLogger("Elevator Spin Motors");
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    elevator.setElevatorMotorSpeed(Constants.ELEVATOR_RISE_SPEED);
  }

  @Override
  public void end(boolean interrupted) {
    elevator.setElevatorMotorSpeed(0);
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.ELEVATOR_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return (elevator.getEncoderValue() >= Constants.ENCODER_THRESHHOLD_ELEVATOR);
  }
}
