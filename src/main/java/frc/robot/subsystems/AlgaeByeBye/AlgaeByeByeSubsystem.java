// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgaeByeBye;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeByeByeSubsystem extends SubsystemBase {
  /** Creates a new AlgaeRemoverSubsystem. */
  private final LoggableSystem<AlgaeByeByeIO, AlgaeByeByeInputs> algaeSystem;

  public AlgaeByeByeSubsystem(AlgaeByeByeIO io) {
    algaeSystem = new LoggableSystem<>(io, new AlgaeByeByeInputs());
  }

  @Override
  public void periodic() {
    algaeSystem.updateInputs();
  }

  public void setRemoverSpeed(double speed) {
    algaeSystem.getIO().setRemoverSpeed(speed);
  }

 
  public void stopRemoverMotors() {
    algaeSystem.getIO().stopRemoverMotors();
  }

  
}
