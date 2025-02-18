package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

// ALL COMMENTED CODE REQUIRES METHODS THAT DON'T EXIST YET

public class ResetElevator extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final TimeoutLogger timeoutCounter;
  private final Timer timer;

  public ResetElevator(ElevatorSubsystem elevator) {
    this.elevator = elevator;
    timeoutCounter = new TimeoutLogger("ResetElevator");
    timer = new Timer();
    addRequirements(elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    elevator.setElevatorMotorSpeed(Constants.ELEVATOR_LOWER_SPEED);
  }

  @Override
  public void end(boolean interrupted) {
    elevator.stopMotor();
    elevator.setElevatorPosition(0);
    elevator.resetEncoder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.ELEVATOR_RESET_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      elevator.setElevatorPosition(0);
      return true;
    }
    return elevator.getReverseLimitSwitchState();
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
