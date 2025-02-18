// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subsystemtests;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral.CoralSubsystem;

public class SetCoralLimitState extends Command {
  private final CoralSubsystem coralSystem;
  private final boolean enableOrDisableLimitSwitch;

  public SetCoralLimitState(CoralSubsystem coralSystem, boolean enableOrDisableLimitSwitch) {
    this.coralSystem = coralSystem;
    this.enableOrDisableLimitSwitch = enableOrDisableLimitSwitch;
    addRequirements(coralSystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    coralSystem.setLimitSwitchState(enableOrDisableLimitSwitch);
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
