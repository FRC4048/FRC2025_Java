// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Supplier;
import org.littletonrobotics.junction.AutoLogOutput;

public class Vision extends SubsystemBase {
  LoggableSystem<VisionIO, VisionInputs> system;
  private final Supplier<Pose2d> pose2dSupplier;
  double[] algaeConfidences = new double[AlgaePositions.values().length];
  ArrayList<AlgaePositions> currentAlgaePosition = new ArrayList<>();
  double[] coralConfidences = new double[BranchPositions.values().length];
  ArrayList<BranchPositions> currentCoralPositions = new ArrayList<>();

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
  }

  private void locateGamePieces() {
    currentAlgaePosition.clear();
    currentCoralPositions.clear();
    int detectionLength = system.getInputs().tx.length;
    if (!system.getInputs().valid) {
      return;
    }
    for (int i = 0; i < detectionLength; i++) {
      String className = system.getInputs().className[i];
      if (className.equalsIgnoreCase("algae")) {
        double[] returnArray = GamePieceLocate.findCoralBranch(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        AlgaePositions algaePos =
            AlgaePositions.values()[(int) returnArray[0]];
        coralConfidences[(int)returnArray[0]]+= returnArray[1]* Constants.PIECE_DETECTION_PROBABILITY_SCALAR;
        if (coralConfidences[(int)returnArray[0]] > Constants.MINUMUM_PIECE_DETECTION_CONFIRMED_DOT) {
          currentAlgaePosition.add(algaePos);
        }
      } else if (className.equalsIgnoreCase("coral")) {
        double[] returnArray = GamePieceLocate.findCoralBranch(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        BranchPositions coralBranch =
            BranchPositions.values()[(int) returnArray[0]];
        coralConfidences[(int)returnArray[0]]+= returnArray[1] * Constants.PIECE_DETECTION_PROBABILITY_SCALAR;
        if (coralConfidences[(int)returnArray[0]] > Constants.MINUMUM_PIECE_DETECTION_CONFIRMED_DOT) {
          currentCoralPositions.add(coralBranch);
        }
      }
    }
  }
}
