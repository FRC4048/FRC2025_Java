// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaeextender;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeExtenderIO extends LoggableIO<AlgaeExtenderInputs> {
  void stopAlgaeExtenderMotor();

  void setAlgaeExtenderSpeed(double speed);

  void resetExtenderEncoder();
}
