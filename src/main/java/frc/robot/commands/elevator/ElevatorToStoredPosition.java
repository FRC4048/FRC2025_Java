package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;
import frc.robot.utils.motor.NeoPidConfig;

// ALL COMMENTED CODE REQUIRES METHODS THAT DON'T EXIST YET

public class ElevatorToStoredPosition extends LoggableCommand {
  private final ElevatorSubsystem elevator;
  private final Timer timer;
  private NeoPidConfig neoPidConfig;

  public ElevatorToStoredPosition(ElevatorSubsystem elevator) {
    timer = new Timer();
    this.elevator = elevator;
    addRequirements(elevator);
    neoPidConfig = new NeoPidConfig(Constants.ELEVATOR_USE_MAX_MOTION);
  }

  @Override
  public void initialize() {
    timer.restart();
    if (elevator.getStoredReefPosition().getElevatorHeight() > elevator.getEncoderValue()) {
      // down pid
      neoPidConfig
          .setP(Constants.ELEVATOR_PID_DOWN_P)
          .setI(Constants.ELEVATOR_PID_DOWN_I)
          .setD(Constants.ELEVATOR_PID_DOWN_D)
          .setFF(Constants.ELEVATOR_PID_DOWN_FF);
    } else {
      neoPidConfig
          .setP(Constants.ELEVATOR_PID_P)
          .setI(Constants.ELEVATOR_PID_I)
          .setD(Constants.ELEVATOR_PID_D)
          .setFF(Constants.ELEVATOR_PID_FF);
      // up pid
    }
    elevator.configurePID(neoPidConfig);
    elevator.setElevatorPosition(elevator.getStoredReefPosition().getElevatorHeight());
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
