package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

public class DeployHarpoon extends LoggableCommand {
  private final ClimberSubsystem climber;
  private final ElevatorSubsystem elevator;
  private Timer timer;
  private final TimeoutLogger timeoutCounter;

  public DeployHarpoon(ClimberSubsystem climber, ElevatorSubsystem elevator) {
    this.climber = climber;
    this.elevator = elevator;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("DeployHarpoon");
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    timer.restart();
  }

  @Override
  public void execute() {
    // need to wait for the elavator to get to safe position
    if (elevator.getEncoderValue()
        < (ElevatorPosition.LEVEL1.getElevatorHeight() + Constants.CLIMBER_ELEVATOR_TOLERANCE)) {
      climber.setClimberSpeed(Constants.CLIMBER_PHASE1_SPEED);
    } else {
      climber.stopClimber();
    }
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopClimber();
  }

  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.CLIMBER_DEPLOY_HARPOON_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return climber.getEncoderPosition() >= Constants.CLIMBER_PHASE1_POSITION;
  }
}
