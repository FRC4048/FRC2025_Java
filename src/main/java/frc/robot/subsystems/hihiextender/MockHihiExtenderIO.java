// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.NeoPidMotorParams;

public class MockHihiExtenderIO implements HihiExtenderIO {

  @Override
  public void stopHihiExtenderMotor() {}

  @Override
  public void setHihiExtenderSpeed(double speed) {}

  @Override
  public void resetExtenderEncoder() {}

  @Override
  public void setExtenderPosition(double encoderPos) {}

  @Override
  public void updateInputs(MotorInputs inputs) {}

  @Override
  public void configurePID(double p, double i, double d) {}

  @Override
  public void configurePID(NeoPidMotorParams params) {}
}
