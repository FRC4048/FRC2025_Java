// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.math.MathUtil;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;
import java.util.function.DoubleSupplier;

public class SetElevatorTargetPosition extends LoggableCommand {

  private final DoubleSupplier targetSupplier;
  private final ElevatorSubsystem elevatorSubsystem;
  private boolean pidLocked = true;

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

    double postDeadbandValue = MathUtil.applyDeadband(targetSupplier.getAsDouble(), Constants.ELEVATOR_MANUAL_DEADBAND);
    if (postDeadbandValue > Constants.ELEVATOR_MANUAL_MAX_SPEED_UP) {
      postDeadbandValue = Constants.ELEVATOR_MANUAL_MAX_SPEED_UP;
    } else if (postDeadbandValue < Constants.ELEVATOR_MANUAL_MAX_SPEED_DOWN) {
      postDeadbandValue = Constants.ELEVATOR_MANUAL_MAX_SPEED_DOWN;
    }

    if (Math.abs(postDeadbandValue) > 0) {
      elevatorSubsystem.setElevatorMotorSpeed(postDeadbandValue);
      pidLocked = false;
    } else if (pidLocked == false) {
      elevatorSubsystem.setElevatorPosition(elevatorSubsystem.getEncoderValue());
      pidLocked = true;
    }
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
