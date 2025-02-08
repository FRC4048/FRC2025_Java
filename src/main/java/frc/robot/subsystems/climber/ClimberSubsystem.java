// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.LoggableSystem;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final LoggableSystem<ClimberIO, ClimberInputs> climberSystem;

  public ClimberSubsystem(ClimberIO io) {
    climberSystem = new LoggableSystem<>(io, new ClimberInputs());
  }

  @Override
  public void periodic() {
    if (Constants.COMMAND_DEBUG) {
      SmartShuffleboard.put("Climber", "Forward", isExtendedLimitSwitchPressed());
      SmartShuffleboard.put("Climber", "Backward", isRetractedLimitSwitchPressed());
    }
    climberSystem.updateInputs();
    if (isExtendedLimitSwitchPressed() || isRetractedLimitSwitchPressed()) {
      stopClimber();
    }
  }

  public void setClimberSpeed(double speed) {
    climberSystem.getIO().setClimberSpeed(speed);
  }

  public void stopClimber() {
    climberSystem.getIO().stopClimber();
  }

  public boolean isRetractedLimitSwitchPressed() {
    return climberSystem.getInputs().climberReversedLimitSwitchPressed;
  }

  public boolean isExtendedLimitSwitchPressed() {
    return climberSystem.getInputs().climberForwardLimitSwitchPressed;
  }
}
