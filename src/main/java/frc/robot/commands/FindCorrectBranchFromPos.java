package frc.robot.commands;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.numbers.N3;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;

public class FindCorrectBranchFromPos {
  private static final BranchPositions[] BRANCHES = BranchPositions.values();
  private static final AlgaePositions[] ALGAES = AlgaePositions.values();
  // Precomputed vectors for positions
  private static final Vector<N3>[] PRECOMPUTED_BRANCH_VECS = precomputePositionVectors(BRANCHES);
  private static final Vector<N3>[] PRECOMPUTED_ALGAE_VECS = precomputePositionVectors(ALGAES);

  // Precompute position vectors for any enum type with a getPosition method
  @SuppressWarnings("unchecked")
  private static <T extends Enum<?>> Vector<N3>[] precomputePositionVectors(T[] values) {
    Vector<N3>[] vectors = new Vector[values.length];
    for (int i = 0; i < values.length; i++) {
      // Using reflection to access getPosition method - assumes the method exists on the enum
      try {
        Pose3d pose = (Pose3d) values[i].getClass().getMethod("getPosition").invoke(values[i]);
        vectors[i] = pose.getTranslation().toVector();
      } catch (Exception e) {
        throw new RuntimeException("Failed to precompute position vectors", e);
      }
    }
    return vectors;
  }

  // PeicePos is in Radians
  public static BranchPositions FindCoralBranch(Pose2d robotPos, Vector<N2> piecePos) {
    return findClosestPosition(robotPos, piecePos, BRANCHES, PRECOMPUTED_BRANCH_VECS);
  }

  public static <T extends Enum<T>> T findClosestPosition(
      Pose2d robotPos, Vector<N2> piecePos, T[] values, Vector<N3>[] preComputedVecs) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    final Vector<N3> cameraPosVec = cameraPos.getTranslation().toVector();
    final Matrix<N3, N3> invCameraRotation = cameraPos.getRotation().unaryMinus().toMatrix();
    final Vector<N3> pieceVec =
        VecBuilder.fill(1, Math.tan(-piecePos.get(1)), Math.tan(piecePos.get(0)));
    double maxDot = -1.0;
    T closest = null;
    for (int i = 0; i < values.length; i++) {
      Matrix<N3, N1> locationVec =
          (invCameraRotation.times(preComputedVecs[i].minus(cameraPosVec)));
      double dot =
          pieceVec.dot(
              VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                  .unit());
      if (dot > maxDot) {
        maxDot = dot;
        closest = values[i];
      }
    }
    return closest;
  }

  // peicepos is in Radians
  public static AlgaePositions FindAlgae(Pose2d robotPos, Vector<N2> piecePos) {
    return findClosestPosition(robotPos, piecePos, ALGAES, PRECOMPUTED_ALGAE_VECS);
  }

  public static BranchPositions UnitTest1() {
    double x = 0.84151552345920325992;
    double y = 0;
    return FindCoralBranch(
        new Pose2d(2.763724485, 4.1902085050000, new Rotation2d(0)), VecBuilder.fill(x, y));
  }
}
