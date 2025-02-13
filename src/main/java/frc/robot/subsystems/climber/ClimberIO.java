// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

/** Add your docs here. */
public interface ClimberIO extends LoggableIO<BuildableFolderMotorInputs<SparkMax>> {

  void setClimberSpeed(double speed);

  void stopClimber();

  void resetClimberEncoder();
}
