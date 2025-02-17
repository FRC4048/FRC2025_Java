// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algaebyebyeroller.AlgaeByeByeRollerSubsystem;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.hihiextender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class CancelAll extends Command {
  /** Creates a new CancelAll. */
  private AlgaeByeByeRollerSubsystem AlgaeByeByeRollerSubsystem;

  private AlgaeByeByeTiltSubsystem AlgaeByeByeTiltSubsystem;
  private ElevatorSubsystem ElevatorSubsystem;
  private HihiExtenderSubsystem HihiExtenderSubsystem;
  private HihiRollerSubsystem HihiRollerSubsystem;
  private CoralSubsystem CoralSubsystem;
  private ClimberSubsystem ClimberSubsystem;

  public CancelAll(
      AlgaeByeByeRollerSubsystem AlgaeByeByeRollerSubsystem,
      AlgaeByeByeTiltSubsystem AlgaeByeByeTiltSubsystem,
      ElevatorSubsystem ElevatorSubsystem,
      HihiExtenderSubsystem HihiExtenderSubsystem,
      HihiRollerSubsystem HihiRollerSubsystem,
      CoralSubsystem CoralSubsystem,
      ClimberSubsystem ClimberSubsystem) {
    this.AlgaeByeByeRollerSubsystem = AlgaeByeByeRollerSubsystem;
    this.AlgaeByeByeTiltSubsystem = AlgaeByeByeTiltSubsystem;
    this.ElevatorSubsystem = ElevatorSubsystem;
    this.HihiExtenderSubsystem = HihiExtenderSubsystem;
    this.HihiRollerSubsystem = HihiRollerSubsystem;
    this.CoralSubsystem = CoralSubsystem;
    this.ClimberSubsystem = ClimberSubsystem;
    addRequirements(AlgaeByeByeRollerSubsystem);
    addRequirements(AlgaeByeByeTiltSubsystem);
    addRequirements(ElevatorSubsystem);
    addRequirements(HihiExtenderSubsystem);
    addRequirements(HihiRollerSubsystem);
    addRequirements(CoralSubsystem);
    addRequirements(ClimberSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

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
