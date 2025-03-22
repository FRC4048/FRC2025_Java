package frc.robot.commands;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.numbers.N3;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;

public class FindCorrectBranchFromPos {
  private static final BranchPositions[] BRANCHES = BranchPositions.values();
  private static final AlgaePositions[] ALGAES = AlgaePositions.values();
  private static final Translation3d[] PRECOMPUTED_BRANCH_VECS;
  private static final Translation3d[] PRECOMPUTED_ALGAE_VECS;

  static {
    PRECOMPUTED_BRANCH_VECS = new Translation3d[BRANCHES.length];
    for (int i = 0; i < BRANCHES.length; i++) {
      PRECOMPUTED_BRANCH_VECS[i] = BRANCHES[i].getPosition().getTranslation();
    }

    PRECOMPUTED_ALGAE_VECS = new Translation3d[ALGAES.length];
    for (int i = 0; i < ALGAES.length; i++) {
      PRECOMPUTED_ALGAE_VECS[i] = ALGAES[i].getPosition().getTranslation();
    }
  }

  // PeicePos is in Radians
  public static BranchPositions FindCoralBranch(Pose2d robotPos, Vector<N2> piecePos) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    final Translation3d cameraPosVec = cameraPos.getTranslation();
    final Rotation3d invCameraRotation = cameraPos.getRotation().unaryMinus();
    final Vector<N3> pieceVec =
        VecBuilder.fill(1, Math.tan(piecePos.get(1)), Math.tan(piecePos.get(0)));
    double maxDot = -1.0;
    BranchPositions closestBranch = null;
    for (int i = 0; i < BRANCHES.length; i++) {
      Vector<N3> branchVec =
          PRECOMPUTED_BRANCH_VECS[i]
              .minus(cameraPosVec)
              .rotateBy(invCameraRotation)
              .toVector()
              .unit();
      double dot = pieceVec.dot(branchVec);
      if (dot > maxDot) {
        maxDot = dot;
        closestBranch = BRANCHES[i];
      }
    }
    return closestBranch;
  }

  // peicepos is in Radians
  public static AlgaePositions FindAlgae(Pose2d robotPos, Vector<N2> piecePos) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    final Translation3d cameraPosVec = cameraPos.getTranslation();
    final Rotation3d invCameraRotation = cameraPos.getRotation().unaryMinus();
    final Vector<N3> pieceVec =
        VecBuilder.fill(1, Math.tan(-piecePos.get(0)), Math.tan(piecePos.get(1)));
    double maxDot = -1.0;
    AlgaePositions closestAlgae = null;
    for (int i = 0; i < ALGAES.length; i++) {
      Vector<N3> algaeVec =
          PRECOMPUTED_ALGAE_VECS[i]
              .minus(cameraPosVec)
              .rotateBy(invCameraRotation)
              .toVector()
              .unit();
      double dot = pieceVec.dot(algaeVec);
      if (dot > maxDot) {
        maxDot = dot;
        closestAlgae = ALGAES[i];
      }
    }
    return closestAlgae;
  }

  public static BranchPositions UnitTest1() {
    double x = 0.84151552345920325992;
    double y = 0;
    return FindCoralBranch(
        new Pose2d(2.763724485, 4.1902085050000, new Rotation2d(0)),
        new Vector<N2>(VecBuilder.fill(x, y)));
  }
}
