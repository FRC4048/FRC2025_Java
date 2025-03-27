// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N12;
import edu.wpi.first.math.numbers.N6;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;
import frc.robot.utils.GamePieceLocate;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import java.util.ArrayList;
import java.util.function.Supplier;
import org.littletonrobotics.junction.AutoLogOutput;

public class Vision extends SubsystemBase {
  LoggableSystem<VisionIO, VisionInputs> system;
  private final Supplier<Pose2d> pose2dSupplier;
  Matrix<N12, N1> algaeConfidences = new Matrix<N12, N1>(Nat.N12(), Nat.N1(), new double[12]);
  ArrayList<AlgaePositions> currentAlgaePosition = new ArrayList<>();
  Matrix<N6, N6> coralConfidences = new Matrix<N6, N6>(Nat.N6(), Nat.N6(), new double[36]);
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
    algaeConfidences.elementPower(Constants.DECAY_CONSTANT);
    coralConfidences.elementPower(Constants.DECAY_CONSTANT);
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
        double[] returnArray =
            GamePieceLocate.findCoralBranch(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        AlgaePositions algaePos = AlgaePositions.values()[(int) returnArray[0]];
        algaeConfidences.set(
            (int) returnArray[0],
            1,
            (algaeConfidences.get((int) (returnArray[0]), 1)
                + Math.pow(returnArray[2], 3)
                    * Math.pow(returnArray[1], 2)
                    * Constants.PIECE_DETECTION_PROBABILITY_SCALAR));
        if (algaeConfidences.get((int) (returnArray[0] / 6), (int) (returnArray[0] % 6))
            > Constants.MINUMUM_PIECE_DETECTION_CONFIRMED_DOT) {
          currentAlgaePosition.add(algaePos);
        }
      } else if (className.equalsIgnoreCase("coral")) {
        double[] returnArray =
            GamePieceLocate.findCoralBranch(
                pose2dSupplier.get(), system.getInputs().tx[i], system.getInputs().ty[i]);
        BranchPositions coralBranch = BranchPositions.values()[(int) returnArray[0]];
        coralConfidences.set(
            ((int) returnArray[0]) / 6,
            ((int) returnArray[1]) % 6,
            Math.pow(returnArray[2], 3)
                * Math.pow(returnArray[1], 2)
                * Constants.PIECE_DETECTION_PROBABILITY_SCALAR);
        if (coralConfidences.get((int) (returnArray[0] / 6), (int) (returnArray[0] % 6))
            > Constants.MINUMUM_PIECE_DETECTION_CONFIRMED_DOT) {
          currentCoralPositions.add(coralBranch);
        }
      }
    }
  }
}
