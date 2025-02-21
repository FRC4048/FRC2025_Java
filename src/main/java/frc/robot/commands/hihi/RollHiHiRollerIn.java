package frc.robot.commands.hihi;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.hihiroller.HihiRollerSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class RollHiHiRollerIn extends LoggableCommand {
  private final HihiRollerSubsystem hihiRoller;
  private final Timer timer;
  private boolean speedArmed = false;

  public RollHiHiRollerIn(HihiRollerSubsystem hihiRoller) {
    this.hihiRoller = hihiRoller;
    timer = new Timer();
    addRequirements(hihiRoller);
  }

  @Override
  public void initialize() {
    hihiRoller.setRollerMotorSpeed(Constants.HIHI_INTAKE_SPEED);
    timer.restart();
    speedArmed = false;
  }

  @Override
  public void execute() {
    if (hihiRoller.getRollerMotorVelocity() > Constants.HIHI_INTAKE_BASE_VELOCITY) {
      // Motor has spun up
      speedArmed = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    hihiRoller.stopHihiRollerMotor();
  }

  @Override
  public boolean isFinished() {
    if (speedArmed && hihiRoller.getRollerMotorVelocity() < Constants.HIHI_INTAKE_BASE_VELOCITY) {
      return true;
    }
    return (timer.hasElapsed(Constants.HIHI_ROLLER_IN_TIMEOUT));
  }
}
