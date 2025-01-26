// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.algaeextender;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeExtenderSubsystem extends SubsystemBase {
  private final LoggableSystem<AlgaeExtenderIO, AlgaeExtenderInputs> system;

  /** Creates a new Extender. */
  public AlgaeExtenderSubsystem(AlgaeExtenderIO io) {
    system = new LoggableSystem<>(io, new AlgaeExtenderInputs());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    system.updateInputs();
  }

  public void setExtenderSpeed(double speed) {
    system.getIO().setAlgaeExtenderSpeed(speed);
  }

  public void stopExtenderMotors() {
    system.getIO().stopAlgae();
  }

  public boolean getForwardSwitchState() {
    return system.getInputs().fwdTripped;
  }

  public boolean getReverseSwitchState() {
    return system.getInputs().revTripped;
  }

  public void resetEncoder() {
    system.getIO().resetExtenderEncoder();
  }
}
