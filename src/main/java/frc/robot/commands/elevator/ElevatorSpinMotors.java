// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class ElevatorSpinMotors extends LoggableCommand {
  /** Creates a new ElevatorSpinMotors. */
  private final ElevatorSubsystem elevator;

  public double startTime;

  public ElevatorSpinMotors(ElevatorSubsystem elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
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
    return (elevator.getEncoderValue1() >= Constants.ENCODER_THRESHHOLD_ELEVATOR
        || elevator.getEncoderValue2() >= Constants.ENCODER_THRESHHOLD_ELEVATOR
        || Timer.getFPGATimestamp() - Constants.ELEVATOR_TIMEOUT >= startTime);
  }
}
