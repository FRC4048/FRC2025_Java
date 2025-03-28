package frc.robot.utils;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import frc.robot.constants.*;
import java.util.Arrays;
import java.util.Comparator;

public class GamePieceLocate {
  public record BranchPositionMeasurement(BranchPositions branchPosition, double confidence) {}

  public record AlgaePositionMeasurement(AlgaePositions algaePosition, double confidence) {}

  private static final BranchPositions[] BRANCHES = BranchPositions.values();
  private static final AlgaePositions[] ALGAES = AlgaePositions.values();
  private static final Comparator<BranchPositions> branchComparator =
      Comparator.comparingInt(b -> b.getElevatorLevel().getWeight());
  // Precomputed vectors for positions
  private static final Vector<N3>[] PRECOMPUTED_BRANCH_VECS =
      Arrays.stream(BRANCHES)
          .map(branch -> branch.getPosition().getTranslation().toVector())
          .toArray(Vector[]::new);
  ;

  private static final Vector<N3>[] PRECOMPUTED_ALGAE_VECS =
      Arrays.stream(ALGAES)
          .map(algae -> algae.getPosition().getTranslation().toVector())
          .toArray(Vector[]::new);

  // piece pos is in DEGREES, not RD
  public static BranchPositionMeasurement findCoralBranch(
      Pose2d robotPos, double piecePosTXDeg, double piecePosTYDeg) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.LIMELIGHT_TO_ROBOT);
    final Vector<N3> cameraPosVec = cameraPos.getTranslation().toVector();
    final Matrix<N3, N3> invCameraRotation = cameraPos.getRotation().unaryMinus().toMatrix();
    final Vector<N3> pieceVec =
        VecBuilder.fill(
                1,
                -Math.tan(Math.toRadians(piecePosTXDeg)),
                Math.tan(Math.toRadians(piecePosTYDeg)))
            .unit();
    double maxDot = Constants.MINIMUM_PIECE_DETECTION_DOT;
    BranchPositions closest = null;
    int n = CenterPositions.getClosest(robotPos);
    for (int i = 6 * n - 3; i < 6 * n + 9; i++) {
      int f = Math.floorMod(i, BRANCHES.length);
      Matrix<N3, N1> locationVec =
          (invCameraRotation.times(PRECOMPUTED_BRANCH_VECS[f].minus(cameraPosVec)));
      double dot =
          pieceVec.dot(
              VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                  .unit());
      if (dot > maxDot) {
        maxDot = dot;
        closest = BRANCHES[f];
      }
    }
    if (closest == null) {
      return null;
    }
    return new BranchPositionMeasurement(closest, maxDot);
  }

  // piece pos is in DEGREES, not RD
  public static AlgaePositionMeasurement findAlgaePos(
      Pose2d robotPos, double piecePosTXDeg, double piecePosTYDeg) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.LIMELIGHT_TO_ROBOT);
    final Vector<N3> cameraPosVec = cameraPos.getTranslation().toVector();
    final Matrix<N3, N3> invCameraRotation = cameraPos.getRotation().unaryMinus().toMatrix();
    final Vector<N3> pieceVec =
        VecBuilder.fill(
                1,
                -Math.tan(Math.toRadians(piecePosTXDeg)),
                Math.tan(Math.toRadians(piecePosTYDeg)))
            .unit();
    double maxDot = Constants.MINIMUM_PIECE_DETECTION_DOT;
    AlgaePositions closest = null;
    int n = CenterPositions.getClosest(robotPos);
    for (int i = 2 * n - 2; i < 2 * n + 4; i++) {
      int f = Math.floorMod(i, ALGAES.length);
      Matrix<N3, N1> locationVec =
          (invCameraRotation.times(PRECOMPUTED_ALGAE_VECS[f].minus(cameraPosVec)));
      double dot =
          pieceVec.dot(
              VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                  .unit());
      if (dot > maxDot) {
        maxDot = dot;
        closest = ALGAES[f];
      }
    }
    if (closest == null) {
      return null;
    }
    return new AlgaePositionMeasurement(closest, maxDot);
  }

  public static ElevatorPosition calculateBranchLevel(
      AlignmentPosition alignmentTrunk,
      BranchPositions[] branchesWithCoral,
      AlgaePositions[] spotsWithAlgae) {
    BranchPositions[] relevantBranches =
        Arrays.stream(branchesWithCoral)
            .filter(b -> b.getTrunk() == alignmentTrunk)
            .sorted(branchComparator.reversed())
            .toArray(BranchPositions[]::new);

    AlgaePositions[] relevantAlgaePositions =
        Arrays.stream(spotsWithAlgae)
            .filter(
                a ->
                    a.getAlignmentPosition1() == alignmentTrunk
                        || a.getAlignmentPosition2() == alignmentTrunk)
            .toArray(AlgaePositions[]::new);
    // find if any algae have been scored high or low.
    boolean isHigh = false;
    boolean isLow = false;
    for (AlgaePositions p : relevantAlgaePositions) {
      if (p.isHigh()) {
        isHigh = true;
      } else {
        isLow = true;
      }
    }
    // find what branches have been scored
    boolean l4Score = false;
    boolean l3Score = false;
    boolean l2Score = false;
    for (BranchPositions b : relevantBranches) {
      switch (b.getElevatorLevel()) {
        case LEVEL4 -> {
          l4Score = true;
        }
        case LEVEL3 -> {
          l3Score = true;
        }
        case LEVEL2 -> {
          l2Score = true;
        }
      }
    }
    if (!l4Score) {
      return ElevatorPosition.LEVEL4;
    } else if (!l3Score && !isLow && !isHigh) {
      return ElevatorPosition.LEVEL3;
    } else if (!l2Score && !isLow) {
      return ElevatorPosition.LEVEL2;
    } else {
      return ElevatorPosition.LEVEL1;
    }
  }
}
