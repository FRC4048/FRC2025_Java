// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.hihi.RetractHiHi;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class CancelAll extends Command {
  /** Creates a new CancelAll. */
  private final ElevatorSubsystem elevatorSubsystem;

  private final HihiExtenderSubsystem hihiExtenderSubsystem;

  public CancelAll(
      ElevatorSubsystem elevatorSubsystem, HihiExtenderSubsystem hihiExtenderSubsystem) {
    this.elevatorSubsystem = elevatorSubsystem;
    this.hihiExtenderSubsystem = hihiExtenderSubsystem;
    addRequirements(elevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    CommandScheduler.getInstance().cancelAll();
    elevatorSubsystem.setElevatorPosition(ElevatorPosition.CORAL_INTAKE.getElevatorHeight());
    // Is this legal?
    CommandScheduler.getInstance().schedule(new RetractHiHi(hihiExtenderSubsystem));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
