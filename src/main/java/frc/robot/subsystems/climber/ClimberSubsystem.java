// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final LoggableSystem<ClimberIO, MotorInputs> climberSystem;

  public ClimberSubsystem(ClimberIO io) {
    MotorInputs inputs = new MotorInputBuilder<>("ClimberSubsystem").addAll().build();
    climberSystem = new LoggableSystem<>(io, inputs);
  }

  @Override
  public void periodic() {
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
    return climberSystem.getInputs().getRevLimit();
  }

  public boolean isExtendedLimitSwitchPressed() {
    return climberSystem.getInputs().getFwdLimit();
  }

  public double getEncoderPosition() {
    return climberSystem.getInputs().getEncoderPosition();
  }

  public void setLimitSwitchState(boolean state) {
    climberSystem.getIO().enableOrDisableLimitSwitch(state);
  }
}
