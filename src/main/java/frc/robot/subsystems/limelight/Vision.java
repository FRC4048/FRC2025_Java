// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class Vision extends SubsystemBase {
  LoggableSystem<VisionIO, VisionInputs> system;

  public Vision(VisionIO io) {
    system = new LoggableSystem<>(io, new VisionInputs("limelight"));
  }

  /**
   * @return The piece's x offset angle in degrees and 0.0 if the piece isn't seen
   */
  public double getPieceOffsetAngleX() {
    return system.getInputs().tx;
  }

  /**
   * @return The piece's y offset angle in degrees and 0.0 if the piece isn't seen
   */
  public double getPieceOffsetAngleY() {
    return system.getInputs().ty;
  }

  public boolean isPieceSeen() {
    return system.getInputs().tv != 0;
  }

  public BranchPositions[] getAllBranchPosition() {
    return system.getInputs().coralSeen;
  }

  public AlgaePositions[] getAllAlgaePosition() {
    return system.getInputs().algaeSeen;
  }

  @Override
  public void periodic() {
    system.updateInputs();
    if (getAllAlgaePosition() != null) {
      SmartShuffleboard.put("peice pos", "Algae", getAllAlgaePosition());
    }
    if (getAllBranchPosition() != null) {
      SmartShuffleboard.put("peice pos", "Coral", getAllBranchPosition());
    }
  }
}
