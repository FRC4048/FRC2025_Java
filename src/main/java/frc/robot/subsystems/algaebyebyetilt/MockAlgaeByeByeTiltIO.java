// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;

/** Add your docs here. */
public class MockAlgaeByeByeTiltIO implements AlgaeByeByeTiltIO {

  @Override
  public void updateInputs(BuildableKeyedInputs<SparkMax> inputs) {}

  @Override
  public void setSpeed(double speed) {}

  @Override
  public void stopMotors() {}

  @Override
  public void resetEncoder() {}
}
