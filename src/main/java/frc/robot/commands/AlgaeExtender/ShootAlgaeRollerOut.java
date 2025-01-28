package frc.robot.commands.AlgaeExtender;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants2025;
import frc.robot.subsystems.hihiExtender.HihiExtenderSubsystem;
import frc.robot.subsystems.hihiRoller.HihiRollerSubsystem;
import frc.robot.utils.logging.LoggableCommand;

public class ShootAlgaeRollerOut extends LoggableCommand {
  /** Creates a new ShootCoral. */
  private final HihiExtenderSubsystem hihiExtender;
  private final HihiRollerSubsystem hihiRoller;
  private double time;

  public ShootAlgaeRollerOut(HihiExtenderSubsystem hihiExtender, HihiRollerSubsystem hihiRoller) {
    this.hihiExtender = hihiExtender;
    this.hihiRoller = hihiRoller;
    addRequirements(hihiExtender, hihiRoller);
  }

  @Override
  public void initialize() {
    hihiRoller.setRollerMotorSpeed(-1 * Constants2025.EXTENDER_ROLLER_SPEED);
    time = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    hihiRoller.stopHihiRollerMotor();
  }

  @Override
  public boolean isFinished() {
    return (Timer.getFPGATimestamp() - time >= 5);
  }
}
