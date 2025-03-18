// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intakeassist;

import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

/** Add your docs here. */
public class MockIntakeAssistIO implements IntakeAssistIO {
  @Override
  public void setSpeed(double speed) {}

  @Override
  public void stopMotors() {}

  @Override
  public void resetIntakeAssistEncoder() {}

  @Override
  public void updateInputs(MotorInputs inputs) {}
}
