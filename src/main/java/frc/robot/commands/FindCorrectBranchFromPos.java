package frc.robot.commands;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.numbers.N3;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;

public class FindCorrectBranchFromPos {
  public static final double POS_TO_FOV_CONVERSION_X =
      Math.tan(Constants.LIMELIGHT_HALF_FOV.getX()) / Constants.LIMELIGHT_HALF_POS.getX();
  public static final double POS_TO_FOV_CONVERSION_Y =
      Math.tan(Constants.LIMELIGHT_HALF_FOV.getY()) / Constants.LIMELIGHT_HALF_POS.getY();
  public static final double SPECIAL_X =
      Constants.LIMELIGHT_HALF_POS.getX() / Math.tan(Constants.LIMELIGHT_HALF_FOV.getX());
  public static final double SPECIAL_Y =
      Constants.LIMELIGHT_HALF_POS.getY() / Math.tan(Constants.LIMELIGHT_HALF_FOV.getY());
  public static final double SPECIAL_RATIO = SPECIAL_Y / SPECIAL_X;
  private static final BranchPositions[] BRANCHES = BranchPositions.values();
  private static final AlgaePositions[] ALGAES = AlgaePositions.values();

  public static BranchPositions FindCoralBranch(Pose2d robotPos, Translation2d piecePos) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    final Rotation3d invCameraRotation = cameraPos.getRotation().unaryMinus();
    final Translation2d pieceTranslation = Constants.LIMELIGHT_HALF_POS.minus(piecePos);

    final Vector<N3> pieceVec =
        VecBuilder.fill(SPECIAL_Y, pieceTranslation.getX() * SPECIAL_RATIO, pieceTranslation.getY())
            .unit();
    double maxDot = -1.0;
    BranchPositions closestBranch = null;
    for (BranchPositions branch : BRANCHES) {
      Vector<N3> branchVec =
          branch
              .getPosition()
              .minus(cameraPos)
              .getTranslation()
              .rotateBy(invCameraRotation)
              .toVector()
              .unit();
      double dot = pieceVec.dot(branchVec);
      if (dot > maxDot) {
        maxDot = dot;
        closestBranch = branch;
      }
    }
    return closestBranch;
  }

  public static AlgaePositions FindAlgae(Pose2d robotPos, Translation2d piecePos) {
    final Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    final Rotation3d invCameraRotation = cameraPos.getRotation().unaryMinus();
    final Translation2d pieceTranslation = Constants.LIMELIGHT_HALF_POS.minus(piecePos);

    final Vector<N3> pieceVec =
        VecBuilder.fill(SPECIAL_Y, pieceTranslation.getX() * SPECIAL_RATIO, pieceTranslation.getY())
            .unit();
    double maxDot = -1.0;
    AlgaePositions closestAlgae = null;
    for (AlgaePositions algae : ALGAES) {
      Vector<N3> algaeVec =
          algae
              .getPosition()
              .minus(cameraPos)
              .getTranslation()
              .rotateBy(invCameraRotation)
              .toVector()
              .unit();
      double dot = pieceVec.dot(algaeVec);
      if (dot > maxDot) {
        maxDot = dot;
        closestAlgae = algae;
      }
    }
    return closestAlgae;
  }
}
