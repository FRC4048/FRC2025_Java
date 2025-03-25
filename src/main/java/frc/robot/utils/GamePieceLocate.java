package frc.robot.utils;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.numbers.N3;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.CenterPositions;
import frc.robot.constants.Constants;
import java.util.Arrays;

public class GamePieceLocate {
  private static final BranchPositions[] BRANCHES = BranchPositions.values();
  private static final AlgaePositions[] ALGAES = AlgaePositions.values();
  // Precomputed vectors for positions
  private static final Translation3d[] PRECOMPUTED_BRANCH_VECS =
      Arrays.stream(BRANCHES)
          .map(branch -> branch.getPosition().getTranslation())
          .toArray(Translation3d[]::new);
  ;

  private static final Translation3d[] PRECOMPUTED_ALGAE_VECS =
      Arrays.stream(ALGAES)
          .map(algae -> algae.getPosition().getTranslation())
          .toArray(Translation3d[]::new);

  // piece pos is in DEGREES, not RD
  public static BranchPositions findCoralBranch(
      Pose2d robotPos, double piecePosTXDeg, double piecePosTYDeg) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.LIMELIGHT_TO_ROBOT);
    final Translation3d cameraPosVec = cameraPos.getTranslation();
    final Rotation3d invCameraRotation = cameraPos.getRotation().unaryMinus();
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
      Vector<N3> locationVec =
          PRECOMPUTED_BRANCH_VECS[f]
              .minus(cameraPosVec)
              .rotateBy(invCameraRotation)
              .toVector()
              .unit();
      double dot = pieceVec.dot(locationVec);
      if (dot > maxDot) {
        maxDot = dot;
        closest = BRANCHES[f];
      }
    }

    return closest;
  }

  // piece pos is in DEGREES, not RD
  public static AlgaePositions findAlgaePos(
      Pose2d robotPos, double piecePosTXDeg, double piecePosTYDeg) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.LIMELIGHT_TO_ROBOT);
    final Translation3d cameraPosVec = cameraPos.getTranslation();
    final Rotation3d invCameraRotation = cameraPos.getRotation().unaryMinus();
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
      Vector<N3> locationVec =
          PRECOMPUTED_ALGAE_VECS[f]
              .minus(cameraPosVec)
              .rotateBy(invCameraRotation)
              .toVector()
              .unit();
      double dot =
          pieceVec.dot(
              VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                  .unit());
      if (dot > maxDot) {
        maxDot = dot;
        closest = ALGAES[f];
      }
    }
    return closest;
  }
}
