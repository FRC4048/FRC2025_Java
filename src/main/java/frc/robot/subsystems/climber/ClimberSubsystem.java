// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.LoggableSystem;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final LoggableSystem<ClimberIO, ClimberInputs> climberSystem;

  public ClimberSubsystem(ClimberIO io) {
    climberSystem = new LoggableSystem<>(io, new ClimberInputs());
  }

  @Override
  public void periodic() {
    climberSystem.updateInputs();
    if (isExtendedLimitSwitchPressed() || isRetractedLimitSwitchPressed()) {
      stopClimber();
    } else {
      climberSystem.getIO().setClimberSpeed(Constants.CLIMBER_CLOSE_SPEED);
    }
  }

  public void setClimberSpeed(double speed) {
    climberSystem.getIO().setClimberSpeed(speed);
  }

  public void stopClimber() {
    climberSystem.getIO().stopClimber();
  }

  public boolean isExtendedLimitSwitchPressed() {
    return climberSystem.getIO().isExtendedLimitSwitchPressed();
  }

  public boolean isRetractedLimitSwitchPressed() {
    return climberSystem.getIO().isRetractedLimitSwitchPressed();
  }
}
