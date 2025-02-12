// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.coral.CoralSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class CoralToREVLimit extends LoggableCommand {
  /** Creates a new byeByeGoToAngle. */
  private final CoralSubsystem coralSystem;

  private final TimeoutLogger timeoutCounter;
  private final Timer timer;

  public CoralToREVLimit(CoralSubsystem coralSystem) {
    this.coralSystem = coralSystem;
    timer = new Timer();
    timeoutCounter = new TimeoutLogger("CoralToREV");
    addRequirements(coralSystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    coralSystem.setShooterSpeed(0.5);
    timer.restart();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    coralSystem.stopShooterMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.BYEBYE_REVERSE_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return (coralSystem.getReverseSwitchState());
  }
}
