// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

/** Add your docs here. */
public interface ClimberIO extends LoggableIO<MotorInputs> {

  void setClimberSpeed(double speed);

  void stopClimber();

  void enableLimitSwitch(boolean state);

  void resetClimberEncoder();
}
