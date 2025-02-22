// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.math.MathUtil;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

public class SetElevatorTargetPosition extends LoggableCommand {

  private final DoubleSupplier targetSupplier;
  private final ElevatorSubsystem elevatorSubsystem;

  public SetElevatorTargetPosition(
      DoubleSupplier targetSupplier, ElevatorSubsystem elevatorSubsystem) {
    this.targetSupplier = targetSupplier;
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double postDeadbandValue = MathUtil.applyDeadband(targetSupplier.getAsDouble(), 0.1);
    elevatorSubsystem.setElevatorPosition(
        -postDeadbandValue + elevatorSubsystem.getEncoderValue());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
