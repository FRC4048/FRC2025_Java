// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;
import frc.robot.utils.logging.LoggableIO;

/** Add your docs here. */
public interface ClimberIO extends LoggableIO<LimitedEncodedMotorInput> {

  void setClimberSpeed(double speed);

  void stopClimber();
}
