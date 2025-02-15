package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

// ALL COMMENTED CODE REQUIRES METHODS THAT DON'T EXIST YET

public class ElevatorToPosition extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final Timer timer;

  public ElevatorToPosition(ElevatorSubsystem elevator) {
    timer = new Timer();
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    timer.restart();
    elevator.setElevatorPosition(elevator.getStoredElevatorHeight().getElevatorHeight());
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
