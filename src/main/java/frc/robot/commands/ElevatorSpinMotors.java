// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorInputs;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ElevatorSpinMotors extends Command {
  /** Creates a new ElevatorSpinMotors. */
  private ElevatorSubsystem elevator;
  private double elevatorMotor1EncoderValue;
  private double elevatorMotor2EncoderValue;

  public ElevatorSpinMotors(ElevatorSubsystem elevator, double elevatorMotor1EncoderValue, double elevatorMotor2EncoderValue ) {
    this.elevator = elevator;
    addRequirements(elevator);
    this.elevatorMotor1EncoderValue = elevatorMotor1EncoderValue;
    this.elevatorMotor2EncoderValue = elevatorMotor2EncoderValue;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  
   
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elevatorMotor1EncoderValue = ElevatorInputs.elevatorMotor1EncoderValue;
    elevatorMotor1EncoderValue = ElevatorInputs.elevatorMotor2EncoderValue;
      elevator.setElevatorMotorSpeed(0.5);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevator.setElevatorMotorSpeed(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (elevatorMotor2EncoderValue >= Constants.ENCODER_THRESHHOLD_ELEVATOR || elevatorMotor1EncoderValue >= Constants.ENCODER_THRESHHOLD_ELEVATOR);
}
}
