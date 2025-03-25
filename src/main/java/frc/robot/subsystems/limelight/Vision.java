// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.shuffleboard.SmartShuffleboard;
import org.littletonrobotics.junction.AutoLogOutput;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Vision extends SubsystemBase {
  LoggableSystem<VisionIO, VisionInputs> system;
    private final Supplier<Pose2d> pose2dSupplier;
    ArrayList<AlgaePositions> currentAlgaePosition = new ArrayList<>();
  ArrayList<BranchPositions> currentCoralPositions = new ArrayList<>();

  public Vision(VisionIO io, Supplier<Pose2d> pose2dSupplier) {
    this.system = new LoggableSystem<>(io, new VisionInputs("limelight"));
    this.pose2dSupplier = pose2dSupplier;
  }

  /**
   * @return The piece's x offset angle in degrees and 0.0 if the piece isn't seen
   */
  public Double getPieceOffsetAngleX(int detectionIndex) {
    if (system.getInputs().tx.length > detectionIndex){
      return system.getInputs().tx[detectionIndex];
    }else{
      return Double.NaN;
    }
  }

  /**
   * @return The piece's y offset angle in degrees and 0.0 if the piece isn't seen
   */
  public double getPieceOffsetAngleY(int detectionIndex

  ) {
    if (system.getInputs().tx.length > detectionIndex){
      return system.getInputs().tx[detectionIndex];
    }else{
      return Double.NaN;
    }
  }

  public boolean isPieceSeen() {
    return system.getInputs().tv != 0;
  }

  @AutoLogOutput
  public BranchPositions[] getAllBranchPosition() {
    return currentCoralPositions.toArray(BranchPositions[]::new);
  }

  @AutoLogOutput
  public AlgaePositions[] getAllAlgaePosition() {
    return currentAlgaePosition.toArray(AlgaePositions[]::new);
  }

  @Override
  public void periodic() {
    system.updateInputs();
    locateGamePieces();
    if (getAllAlgaePosition() != null) {
      SmartShuffleboard.put("peice pos", "Algae", getAllAlgaePosition());
    }
    if (getAllBranchPosition() != null) {
      SmartShuffleboard.put("peice pos", "Coral", getAllBranchPosition());
    }
  }

  private void locateGamePieces() {
    currentAlgaePosition.clear();
    currentCoralPositions.clear();
    //to implement detection

  }
}
