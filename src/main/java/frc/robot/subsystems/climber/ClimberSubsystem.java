// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.inputs.ServoInputs;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  private final LoggableSystem<ClimberIO, MotorInputs> climberSystem;

  private final LoggableSystem<RatchetIO, ServoInputs> ratchetSystem;

  public ClimberSubsystem(ClimberIO motorIO, RatchetIO ratchetIO) {
    MotorInputs motorInputs = new MotorInputBuilder<>("ClimberSubsystem/Climber").addAll().build();
    ServoInputs ratchetInputs = new ServoInputs("ClimberSubsystem/Ratchet");
    climberSystem = new LoggableSystem<>(motorIO, motorInputs);
    ratchetSystem = new LoggableSystem<>(ratchetIO, ratchetInputs);
  }

  @Override
  public void periodic() {
    if (Constants.CLIMBER_DEBUG) {
      SmartShuffleboard.put("Climber", "Forward", isExtendedLimitSwitchPressed());
      SmartShuffleboard.put("Climber", "Backward", isRetractedLimitSwitchPressed());
    }
    climberSystem.updateInputs();
    ratchetSystem.updateInputs();
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

  public void engageRatchet() {
    ratchetSystem.getIO().engageRatchet();
  }

  public void disengageRatchet() {
    ratchetSystem.getIO().disengageRatchet();
  }
}
