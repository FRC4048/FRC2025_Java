// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.MotorName;
import frc.robot.utils.motor.NeoPidConfig;

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
  public void configurePID(NeoPidConfig pidConfig) {}

  @Override
  public void updateInputs(PidMotorInputs inputs) {}

  @Override
  public NeoPidConfig getPIDConfig() {
    return new NeoPidConfig(MotorName.Neo550, true);
  }
}
