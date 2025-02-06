package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.constants.CoralDeposit;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.LoggableCommand;
import frc.robot.utils.logging.TimeoutLogger;

// ALL COMMENTED CODE REQUIRES METHODS THAT DON'T EXIST YET

public class ElevatorToPosition extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final CoralDeposit targetPosition;
  private final Timer timer;
  private final TimeoutLogger timeoutCounter;

  public ElevatorToPosition(ElevatorSubsystem elevator, CoralDeposit targetPosition) {
    timeoutCounter = new TimeoutLogger("Elevator To Position");
    timer = new Timer();
    this.elevator = elevator;
    this.targetPosition = targetPosition;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    // elevator.setElevatorPosition(targetPosition.getHeight());
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.ELEVATOR_TO_POSITION_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return (((elevator.getEncoderValue1()
            >= (targetPosition.getElevatorHeight() - Constants.ELEVATOR_MIN_WINDOW))
        && (elevator.getEncoderValue1()
            <= (targetPosition.getElevatorHeight() + Constants.ELEVATOR_MAX_WINDOW))));
  }
}
