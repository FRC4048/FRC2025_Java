package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class ShootHiHiRollerOut extends LoggableCommand {
  private final HihiRollerSubsystem hihiRoller;
  private final Timer timer;

  public ShootHiHiRollerOut(HihiRollerSubsystem hihiRoller) {
    this.hihiRoller = hihiRoller;
    timer = new Timer();
    addRequirements(hihiRoller);
  }

  @Override
  public void initialize() {
    timer.restart();
    hihiRoller.setRollerMotorSpeed(Constants.HIHI_SHOOT_SPEED);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    hihiRoller.stopHihiRollerMotor();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(Constants.HIHI_ROLLER_OUT_TIMEOUT);
  }
}
