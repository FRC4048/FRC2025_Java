// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import frc.robot.utils.logging.PIDLoggableIO;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import frc.robot.utils.motor.NeoPidConfig;

public interface HihiExtenderIO extends PIDLoggableIO {
  void stopHihiExtenderMotor();

  void setHihiExtenderSpeed(double speed);

  void resetExtenderEncoder();

  void setExtenderPosition(double encoderPos);

  void updateInputs(PidMotorInputs inputs);

  NeoPidConfig getPIDConfig();
}
