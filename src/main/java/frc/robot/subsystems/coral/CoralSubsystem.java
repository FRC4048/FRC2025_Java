// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coral;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class CoralSubsystem extends SubsystemBase {
  private final LoggableSystem<CoralIO, CoralInputs> coralSystem;

  /** Creates a new Shooter. */
  public CoralSubsystem(CoralIO io) {
    coralSystem = new LoggableSystem<>(io, new CoralInputs("CoralSubsystem"));
  }

  @Override
  public void periodic() {
    coralSystem.updateInputs();
    SmartShuffleboard.put("coral", "ForwardTripped?", coralSystem.getInputs().fwdTripped);
    SmartShuffleboard.put("coral", "ReverseTripped?", coralSystem.getInputs().revTripped);
  }

  public void setShooterSpeed(double speed) {
    coralSystem.getIO().setShooterSpeed(speed);
  }

  public void stopShooterMotors() {
    coralSystem.getIO().stopShooterMotors();
  }

  public void enableOrDisableLimitSwitch(boolean state) {
    coralSystem.getIO().enableOrDisableLimitSwitch(state);
  }

  public boolean getForwardSwitchState() {
    return coralSystem.getInputs().fwdTripped;
  }

  public boolean getReverseSwitchState() {
    return coralSystem.getInputs().revTripped;
  }
}
