package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.constants.CoralDeposit;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.LoggableCommand;

// ALL COMMENTED CODE REQUIRES METHODS THAT DON'T EXIST YET

public class ElevatorToPosition extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final CoralDeposit targetPosition;
  private double startTime;

  public ElevatorToPosition(ElevatorSubsystem elevator, CoralDeposit targetPosition) {
    this.elevator = elevator;
    this.targetPosition = targetPosition;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    // elevator.setElevatorPosition(targetPosition.getHeight());
  }

  @Override
  public boolean isFinished() {
    return (((elevator.getEncoderValue1() >= (targetPosition.getHeight() - 1))
            && (elevator.getEncoderValue1() <= (targetPosition.getHeight() + 1)))
        || (Timer.getFPGATimestamp() - Constants.ELEVATOR_TIMEOUT >= startTime));
  }
}
