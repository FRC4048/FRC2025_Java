// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ElevatorSpinMotors extends Command {
  /** Creates a new ElevatorSpinMotors. */
  private ElevatorSubsystem elevator;
public double startTime;

  public ElevatorSpinMotors(ElevatorSubsystem elevator) {
    this.elevator = elevator;
    addRequirements(elevator);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  startTime = Timer.getFPGATimestamp();
   
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    elevator.setElevatorMotorSpeed(Constants.ELEVATOR_RISE_SPEED);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevator.setElevatorMotorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (elevator.getEncoderValue1() >= Constants.ENCODER_THRESHHOLD_ELEVATOR || elevator.getEncoderValue2() >= Constants.ENCODER_THRESHHOLD_ELEVATOR || Timer.getFPGATimestamp() - Constants.ELEVATOR_SPIN_MOTOR_TIMER_THRESHHOLD >= startTime);
  }

}
