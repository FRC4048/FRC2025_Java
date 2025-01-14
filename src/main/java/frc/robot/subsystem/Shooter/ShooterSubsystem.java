// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.util.LoggableSystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ShooterSubsystem extends SubsystemBase {
  private final LoggableSystem<ShooterIO, ShooterInputs> system;
  /** Creates a new Shooter. */
  public ShooterSubsystem(ShooterIO io) {
    system = new LoggableSystem<>(io, new ShooterInputs());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    system.updateInputs();
  }
  public void setAngle(double angleSpeed) {
    system.getIO().setTiltAngularVelocity(angleSpeed);
  }
  public void setShooterSpeed(double speed) {
    system.getIO().setShooterSpeed(speed);
  }

  public void stopShooterMotors() {
    system.getIO().stopShooterMotors();
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
  public double getDesiredTiltAngularVelocity() {
    return system.getInputs().angleSpeed;
  }
  public void resetEncoder() {
    system.getIO().resetTiltEncoder();
  }

}