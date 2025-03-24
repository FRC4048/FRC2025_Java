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
import frc.robot.constants.CenterPositions;
import frc.robot.constants.Constants;
import java.util.Arrays;

public class GamePieceLocate {
  private static final BranchPositions[] BRANCHES = BranchPositions.values();
  private static final AlgaePositions[] ALGAES = AlgaePositions.values();
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

  // Piece Pos is in Radians
  public static BranchPositions findCoralBranch(Pose2d robotPos, Vector<N2> piecePos) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.LIMELIGHT_TO_ROBOT);
    final Vector<N3> cameraPosVec = cameraPos.getTranslation().toVector();
    final Matrix<N3, N3> invCameraRotation = cameraPos.getRotation().unaryMinus().toMatrix();
    final Vector<N3> pieceVec =
        VecBuilder.fill(1, -Math.tan(piecePos.get(1)), -Math.tan(piecePos.get(0)));
    double maxDot = -1.0;
    BranchPositions closest = null;
    int n = CenterPositions.getClosest(robotPos);
    if (n == 0) {
      for (int i = 0; i < 9; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_BRANCH_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = BRANCHES[i];
        }
      }
      for (int i = 33; i < 36; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_BRANCH_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = BRANCHES[i];
        }
      }
    } else if (n == 5) {
      for (int i = 0; i < 3; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_BRANCH_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = BRANCHES[i];
        }
      }
      for (int i = 6 * n - 3; i < 6 * n + 6; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_BRANCH_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = BRANCHES[i];
        }
      }
    } else {
      for (int i = 6 * n - 3; i < 6 * n + 9; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_BRANCH_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = BRANCHES[i];
        }
      }
    }
    return closest;
  }

  // piece pos is in Radians
  public static AlgaePositions findAlgaePos(Pose2d robotPos, Vector<N2> piecePos) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.LIMELIGHT_TO_ROBOT);
    final Vector<N3> cameraPosVec = cameraPos.getTranslation().toVector();
    final Matrix<N3, N3> invCameraRotation = cameraPos.getRotation().unaryMinus().toMatrix();
    final Vector<N3> pieceVec =
        VecBuilder.fill(1, -Math.tan(piecePos.get(1)), -Math.tan(piecePos.get(0)));
    double maxDot = -1.0;
    AlgaePositions closest = null;
    int n = CenterPositions.getClosest(robotPos);
    if (n == 0) {
      for (int i = 0; i < 6; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_ALGAE_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = ALGAES[i];
        }
      }
      for (int i = 10; i < 12; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_ALGAE_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = ALGAES[i];
        }
      }
    } else if (n == 5) {
      for (int i = 0; i < 3; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_ALGAE_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = ALGAES[i];
        }
      }
      for (int i = 8; i < 12; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_ALGAE_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = ALGAES[i];
        }
      }
    } else {
      for (int i = 2 * n - 2; i < 2 * n + 4; i++) {
        Matrix<N3, N1> locationVec =
            (invCameraRotation.times(PRECOMPUTED_ALGAE_VECS[i].minus(cameraPosVec)));
        double dot =
            pieceVec.dot(
                VecBuilder.fill(locationVec.get(0, 0), locationVec.get(1, 0), locationVec.get(2, 0))
                    .unit());
        if (dot > maxDot) {
          maxDot = dot;
          closest = ALGAES[i];
        }
      }
    }
    return closest;
  }

  public static AlgaePositions UnitTest1() {
    double x = 0.31119220563058896357;
    double y = 0.06719517620178168871;
    return findAlgaePos(new Pose2d(2.614524485, 4.0259, new Rotation2d(0)), VecBuilder.fill(x, y));
  }
}
