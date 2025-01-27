// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaeextender;

public class MockAlgaeExtenderIO implements AlgaeExtenderIO {

  @Override
  public void stopAlgaeExtenderMotor() {}

  @Override
  public void setAlgaeExtenderSpeed(double speed) {}

  @Override
  public void resetExtenderEncoder() {}

  @Override
  public void updateInputs(AlgaeExtenderInputs inputs) {}
}
