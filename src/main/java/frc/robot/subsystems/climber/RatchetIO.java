// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.ServoInputs;

/** Add your docs here. */
public interface RatchetIO extends LoggableIO<ServoInputs> {

  void engageRatchet();

  void disengageRatchet();
}
