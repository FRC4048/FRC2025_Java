// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import frc.robot.utils.logging.subsystem.builders.BuildableKeyedMotorInputs;

/** Add your docs here. */
public class MockClimberIO implements ClimberIO {
  @Override
  public void setClimberSpeed(double speed) {}

  @Override
  public void stopClimber() {}

  @Override
  public void updateInputs(BuildableKeyedMotorInputs inputs) {}
}
