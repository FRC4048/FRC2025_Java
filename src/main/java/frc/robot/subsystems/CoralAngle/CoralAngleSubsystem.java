// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.CoralAngle;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class CoralAngleSubsystem extends SubsystemBase {
  private final LoggableSystem<CoralAngleIO, CoralAngleInput> system;

  /** Creates a new Shooter. */
  public CoralAngleSubsystem(CoralAngleIO io) {
    system = new LoggableSystem<>(io, new CoralAngleInput());
  }

  @Override
  public void periodic() {
    system.updateInputs();
  }

  public void setAngle(double angleSpeed) {
    system.getIO().setTiltAngularVelocity(angleSpeed);
  }

  public void stopTiltMotors() {
    system.getIO().stopTiltMotors();
  }

  public double getAngle() {
    return system.getInputs().tiltEncoderPosition;
  }

  public boolean getForwardSwitchState() {
    return system.getInputs().fwdTripped;
  }

  public boolean getReverseSwitchState() {
    return system.getInputs().revTripped;
  }

  public void resetEncoder() {
    system.getIO().resetTiltEncoder();
  }
}
