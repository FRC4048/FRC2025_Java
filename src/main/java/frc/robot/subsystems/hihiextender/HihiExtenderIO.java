// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hihiextender;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableKeyedMotorInputs;

public interface HihiExtenderIO extends LoggableIO<BuildableKeyedMotorInputs<SparkMax>> {
  void stopHihiExtenderMotor();

  void setHihiExtenderSpeed(double speed);

  void resetExtenderEncoder();
}
