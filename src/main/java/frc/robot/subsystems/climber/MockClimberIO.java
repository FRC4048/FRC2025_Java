// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;

/** Add your docs here. */
public class MockClimberIO implements ClimberIO {
  @Override
  public void setClimberSpeed(double speed) {}

  @Override
  public void stopClimber() {}

  @Override
  public void resetClimberEncoder() {}

  @Override
  public void updateInputs(MotorInputs<SparkMax> inputs) {}
}
