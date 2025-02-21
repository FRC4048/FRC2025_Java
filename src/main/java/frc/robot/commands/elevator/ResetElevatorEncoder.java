package frc.robot.commands.elevator;

import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ResetElevatorEncoder extends LoggableCommand {

  private final ElevatorSubsystem subsystem;

  public ResetElevatorEncoder(ElevatorSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    subsystem.resetEncoder();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
