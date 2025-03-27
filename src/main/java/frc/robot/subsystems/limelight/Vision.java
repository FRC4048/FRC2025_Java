// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import java.util.ArrayList;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

public class Vision extends SubsystemBase {
  LoggableSystem<VisionIO, VisionInputs> system;
  private final Supplier<Pose2d> pose2dSupplier;
  ArrayList<GamePieceLocate.AlgaePositionMeasurement> currentAlgaePosition = new ArrayList<>();
  ArrayList<GamePieceLocate.BranchPositionMeasurement> currentCoralPositions = new ArrayList<>();
  double keepTicks = 0;

  public Vision(VisionIO io, Supplier<Pose2d> pose2dSupplier) {
    this.system = new LoggableSystem<>(io, new VisionInputs("limelight"));
    this.pose2dSupplier = pose2dSupplier;
  }

  /**
   * @return The piece's x offset angle in degrees and 0.0 if the piece isn't seen
   */
  public double[] getPieceOffsetAngleX(int detectionIndex) {
    return system.getInputs().tx;
  }

  /**
   * @return The piece's y offset angle in degrees and 0.0 if the piece isn't seen
   */
  public double[] getPieceOffsetAngleY() {
    return system.getInputs().ty;
  }

  public boolean isPieceSeen() {
    return system.getInputs().valid;
  }

  public BranchPositions[] getAllBranchPosition() {
    return currentCoralPositions.stream()
        .map(GamePieceLocate.BranchPositionMeasurement::branchPosition)
        .toArray(BranchPositions[]::new);
  }

  public AlgaePositions[] getAllAlgaePosition() {
    return currentAlgaePosition.stream()
        .map(GamePieceLocate.AlgaePositionMeasurement::algaePosition)
        .toArray(AlgaePositions[]::new);
  }

  public GamePieceLocate.BranchPositionMeasurement[] getAllBranchMeasurements() {
    return currentCoralPositions.toArray(GamePieceLocate.BranchPositionMeasurement[]::new);
  }

  public GamePieceLocate.AlgaePositionMeasurement[] getAllAlgaeMeasurements() {
    return currentAlgaePosition.toArray(GamePieceLocate.AlgaePositionMeasurement[]::new);
  }

  @Override
  public void periodic() {
    system.updateInputs();
    if (Constants.ENABLE_FANCY_LIMELIGHT_MATH) {
      locateGamePieces();
      Logger.recordOutput("coralPoses", getAllBranchMeasurements());
      Logger.recordOutput("algaePoses", getAllAlgaeMeasurements());
    }
  }

  private void locateGamePieces() {
    keepTicks++;
    if (keepTicks > 10) {
      keepTicks = 0;
      currentAlgaePosition.clear();
      currentCoralPositions.clear();
    }
    int detectionLength = system.getInputs().tx.length;
    if (!system.getInputs().valid) {
      return;
    }
    for (int i = 0; i < detectionLength; i++) {
      String className = system.getInputs().className[i];
      if (className.equalsIgnoreCase("coral")) {
        GamePieceLocate.BranchPositionMeasurement coralBranch =
            GamePieceLocate.findCoralBranch(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        if (coralBranch != null) {
          currentCoralPositions.add(coralBranch);
        }
      } else if (className.equalsIgnoreCase("algae")) {
        GamePieceLocate.AlgaePositionMeasurement algaePos =
            GamePieceLocate.findAlgaePos(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        if (algaePos != null) {
          currentAlgaePosition.add(algaePos);
        }
      }
    }
  }
}
