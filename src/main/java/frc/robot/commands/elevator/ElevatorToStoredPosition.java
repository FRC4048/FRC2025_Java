package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ElevatorToStoredPosition extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final Timer timer;

  public ElevatorToStoredPosition(ElevatorSubsystem elevator) {
    timer = new Timer();
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    timer.restart();
    elevator.setElevatorPosition(elevator.getStoredReefPosition().getElevatorHeight());
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
