// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.byebye;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.algaebyebyetilt.AlgaeByeByeTiltSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.utils.logging.TimeoutLogger;
import frc.robot.utils.logging.commands.LoggableCommand;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ByeByeToFwrLimit extends LoggableCommand {
  /** Creates a new byeByeGoToAngle. */
  private final AlgaeByeByeTiltSubsystem tiltMotor;

  private final TimeoutLogger timeoutCounter;
  private final Timer timer;

  public ByeByeToFwrLimit(AlgaeByeByeTiltSubsystem tiltMotor) {
    this.tiltMotor = tiltMotor;
    this.timer = new Timer();
    timeoutCounter = new TimeoutLogger("ByeBye to fwr limit");
    addRequirements(tiltMotor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      tiltMotor.setSpeed(Constants.BYEBYE_FORWARD_SPEED);
    timer.restart();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    tiltMotor.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer.hasElapsed(Constants.BYEBYE_FORWARD_TIMEOUT)) {
      timeoutCounter.increaseTimeoutCount();
      return true;
    }
    return (tiltMotor.getForwardSwitchState());
  }
}
