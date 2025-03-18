// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.constants.Constants;
import frc.robot.subsystems.intakeassist.IntakeAssistSubsystem;
import frc.robot.utils.logging.commands.LoggableCommand;

public class RunIntakeAssist extends LoggableCommand {
  /** Creates a new RunIntakeAssist. */
  private final IntakeAssistSubsystem intake;

  private final double intakeSpeed;
  private final Timer timer;

  public RunIntakeAssist(IntakeAssistSubsystem intake, double intakeSpeed) {
    this.intakeSpeed = intakeSpeed;
    this.timer = new Timer();
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    intake.setIntakeAssistSpeed(intakeSpeed);
    timer.restart();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    intake.stopIntakeAssistMotors();
  }

  @Override
  public boolean isFinished() {
    return (timer.hasElapsed(Constants.INTAKE_ASSIST_TIMEOUT));
  }
}
