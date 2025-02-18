package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class RollHiHiRollerIn extends LoggableCommand {
  private final HihiRollerSubsystem hihiRoller;
  private final Timer timer;

  public RollHiHiRollerIn(HihiRollerSubsystem hihiRoller) {
    this.hihiRoller = hihiRoller;
    timer = new Timer();
    addRequirements(hihiRoller);
  }

  @Override
  public void initialize() {
    double targetVelocity = hihiRoller.getTargetVelocity();
    hihiRoller.setRollerMotorSpeed(targetVelocity);
    timer.restart();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    hihiRoller.stopHihiRollerMotor();
  }

  @Override
  public boolean isFinished() {
    return (timer.hasElapsed(Constants.HIHI_ROLLER_IN_TIMEOUT));
  }
}
