// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.NeoPidConfig;

public interface HihiExtenderIO extends LoggableIO<MotorInputs> {
  void stopHihiExtenderMotor();

  void setHihiExtenderSpeed(double speed);

  void resetExtenderEncoder();

  void setExtenderPosition(double encoderPos);

  void configurePID(double p, double i, double d);

  void configurePID(NeoPidConfig params);
}
