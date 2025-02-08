// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyeroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.BuildableKeyedMotorInputs;

/** Add your docs here. */
public class MockAlgaeByeByeRollerIO implements AlgaeByeByeRollerIO {
  @Override
  public void setSpeed(double speed) {}

  @Override
  public void stopMotors() {}

  @Override
  public void updateInputs(BuildableKeyedMotorInputs<SparkMax> inputs) {}
}
