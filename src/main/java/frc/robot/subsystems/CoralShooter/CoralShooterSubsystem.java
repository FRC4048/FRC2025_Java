// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.CoralShooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class CoralShooterSubsystem extends SubsystemBase {
  private final LoggableSystem<CoralShooterIO, CoralShooterInput> system;

  /** Creates a new Shooter. */
  public CoralShooterSubsystem(CoralShooterIO io) {
    system = new LoggableSystem<>(io, new CoralShooterInput());
  }

  @Override
  public void periodic() {
    system.updateInputs();
  }

  public void setShooterSpeed(double speed) {
    system.getIO().setShooterSpeed(speed);
  }

  public void stopShooterMotors() {
    system.getIO().stopShooterMotors();
  }
  
}
