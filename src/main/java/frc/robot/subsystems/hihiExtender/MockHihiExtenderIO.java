// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiExtender;

public class MockHihiExtenderIO implements HihiExtenderIO {

  @Override
  public void stopHihiExtenderMotor() {}

  @Override
  public void setHihiExtenderSpeed(double speed) {}

  @Override
  public void resetExtenderEncoder() {}

  @Override
  public void updateInputs(HihiExtenderInputs inputs) {}
}
