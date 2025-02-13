// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;
import frc.robot.utils.logging.subsystem.builders.SparkMaxInputBuilder;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final LoggableSystem<ClimberIO, BuildableFolderMotorInputs<SparkMax>> climberSystem;

  public ClimberSubsystem(ClimberIO io) {
    SparkMaxInputBuilder builder = new SparkMaxInputBuilder("ClimberSubsystem");
    BuildableFolderMotorInputs<SparkMax> inputs = builder.addAll().build();
    climberSystem = new LoggableSystem<>(io, inputs);
  }

  @Override
  public void periodic() {
    if (Constants.CLIMBER_DEBUG) {
      SmartShuffleboard.put("Climber", "Forward", isExtendedLimitSwitchPressed());
      SmartShuffleboard.put("Climber", "Backward", isRetractedLimitSwitchPressed());
    }
    climberSystem.updateInputs();
    if (isExtendedLimitSwitchPressed() || isRetractedLimitSwitchPressed()) {
      stopClimber();
    }
  }

  public void resetClimberEncoder() {
    climberSystem.getIO().resetClimberEncoder();
  }

  public void setClimberSpeed(double speed) {
    climberSystem.getIO().setClimberSpeed(speed);
  }

  public void stopClimber() {
    climberSystem.getIO().stopClimber();
  }

  public boolean isRetractedLimitSwitchPressed() {
    return climberSystem.getInputs().revLimit();
  }

  public boolean isExtendedLimitSwitchPressed() {
    return climberSystem.getInputs().fwdLimit();
  }
}
