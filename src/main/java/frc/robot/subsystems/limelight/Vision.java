// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.subsystems.swervev3.SwerveDrivetrain;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import java.util.ArrayList;

public class Vision extends SubsystemBase {
  LoggableSystem<VisionIO, VisionInputs> system;
  SwerveDrivetrain drivetrain;

  public Vision(VisionIO io, SwerveDrivetrain drivetrain) {
    system = new LoggableSystem<>(io, new VisionInputs("limelight"));
    this.drivetrain = drivetrain;
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

  public ArrayList<BranchPositions> getAllBranchPosition() {
    return system.getInputs().coralSeen;
  }

  public ArrayList<AlgaePositions> getAllAlgaePosition() {
    return system.getInputs().algaeSeen;
  }

  @Override
  public void periodic() {
    system.updateInputs();
  }
}
