// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.CommonMotorInputs;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkMaxInputBuilder;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final LoggableSystem<ClimberIO, BuildableFolderMotorInputs<SparkMax>> climberSystem;

  public ClimberSubsystem(ClimberIO io) {
    SparkMaxInputBuilder builder = new SparkMaxInputBuilder("ClimberSubsystem");
    CommonMotorInputs.hasEncoder(builder);
    CommonMotorInputs.hasLimits(builder);
    CommonMotorInputs.hasStatus(builder);
    BuildableFolderMotorInputs<SparkMax> inputs = builder.build();
    climberSystem = new LoggableSystem<>(io, inputs);
  }

  @Override
  public void periodic() {
    climberSystem.updateInputs();
  }

  public void setClimberSpeed(double speed) {
    climberSystem.getIO().setClimberSpeed(speed);
  }

  public void stopClimber() {
    climberSystem.getIO().stopClimber();
  }
}
