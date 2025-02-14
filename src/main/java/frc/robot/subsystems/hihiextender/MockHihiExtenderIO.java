// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;

public class MockHihiExtenderIO implements HihiExtenderIO {

  @Override
  public void stopHihiExtenderMotor() {}

  @Override
  public void setHihiExtenderSpeed(double speed) {}

  @Override
  public void resetExtenderEncoder() {}

  @Override
  public void updateInputs(MotorInputs<SparkMax> inputs) {}
}
