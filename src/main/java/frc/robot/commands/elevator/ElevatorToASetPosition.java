package frc.robot.commands.elevator;

import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;


public class ElevatorToASetPosition extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final ReefPosition reefPosition;

  public ElevatorToASetPosition(ElevatorSubsystem elevator, ReefPosition reefPosition) {
    this.reefPosition = reefPosition;
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    elevator.setElevatorPosition(reefPosition.getElevatorHeight());
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
