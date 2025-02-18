package frc.robot.commands.elevator;

import frc.robot.constants.ReefPosition;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

// ALL COMMENTED CODE REQUIRES METHODS THAT DON'T EXIST YET

public class ResetElevator extends LoggableCommand {
  private final ElevatorSubsystem elevator;

  public ResetElevator(ElevatorSubsystem elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    elevator.setElevatorPosition(ReefPosition.LEVEL0.getElevatorHeight());
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
