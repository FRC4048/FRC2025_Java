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
import java.util.Arrays;

public class GamePieceLocate {
  private static final BranchPositions[] BRANCHES = BranchPositions.values();
  private static final AlgaePositions[] ALGAES = AlgaePositions.values();
  // Precomputed vectors for positions
  private static final Vector<N3>[] PRECOMPUTED_BRANCH_VECS =
      Arrays.stream(BranchPositions.values())
          .map(branch -> branch.getPosition().getTranslation().toVector())
          .toArray(Vector[]::new);
  ;

  private static final Vector<N3>[] PRECOMPUTED_ALGAE_VECS =
      Arrays.stream(AlgaePositions.values())
          .map(algae -> algae.getPosition().getTranslation().toVector())
          .toArray(Vector[]::new);

  // PeicePos is in Radians
  public static BranchPositions findCoralBranch(Pose2d robotPos, Vector<N2> piecePos) {
    return findClosestPosition(robotPos, piecePos, BRANCHES, PRECOMPUTED_BRANCH_VECS);
  }

  public static <T extends Enum<T>> T findClosestPosition(
      Pose2d robotPos, Vector<N2> piecePos, T[] values, Vector<N3>[] preComputedVecs) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    final Vector<N3> cameraPosVec = cameraPos.getTranslation().toVector();
    final Matrix<N3, N3> invCameraRotation = cameraPos.getRotation().unaryMinus().toMatrix();
    final Vector<N3> pieceVec =
        VecBuilder.fill(1, -Math.tan(piecePos.get(1)), -Math.tan(piecePos.get(0)));
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
  public static AlgaePositions findAlgaePos(Pose2d robotPos, Vector<N2> piecePos) {
    return findClosestPosition(robotPos, piecePos, ALGAES, PRECOMPUTED_ALGAE_VECS);
  }

  public static AlgaePositions UnitTest1() {
    double x = 0.31119220563058896357;
    double y = 0.06719517620178168871;
    return findAlgaePos(new Pose2d(2.614524485, 4.0259, new Rotation2d(0)), VecBuilder.fill(x, y));
  }
}
