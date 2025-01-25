// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.AlgaeRemover;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.logging.LoggableSystem;

public class AlgaeRemoverSubsystem extends SubsystemBase {
  /** Creates a new AlgaeRemoverSubsystem. */
  private final LoggableSystem<AlgaeRemoverIO, AlgaeRemoverInputs> algaeSystem;
  public AlgaeRemoverSubsystem(AlgaeRemoverIO io) {
    algaeSystem = new LoggableSystem<>(io, new AlgaeRemoverInputs());
  }

  @Override
  public void periodic() {
   algaeSystem.updateInputs();
  }




}
