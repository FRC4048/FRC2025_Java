// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;
import frc.robot.utils.logging.LoggableIO;

public interface HihiExtenderIO extends LoggableIO<LimitedEncodedMotorInput> {
  void stopHihiExtenderMotor();

  void setHihiExtenderSpeed(double speed);

  void resetExtenderEncoder();
}
