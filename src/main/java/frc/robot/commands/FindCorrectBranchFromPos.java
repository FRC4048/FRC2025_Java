package frc.robot.commands;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.numbers.N3;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;

public class FindCorrectBranchFromPos {
  public static final double POS_TO_FOV_CONVERSION_X =
      Math.tan(Constants.LIMELIGHT_HALF_FOV.getX()) / Constants.LIMELIGHT_HALF_POS.getX();
  public static final double POS_TO_FOV_CONVERSION_Y =
      Math.tan(Constants.LIMELIGHT_HALF_FOV.getY()) / Constants.LIMELIGHT_HALF_POS.getY();

  public BranchPositions FindBranch(Pose2d robotPos, Translation2d piecePos) {
    Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    Rotation3d invCameraRotation = cameraPos.getRotation().unaryMinus();
    Translation2d pieceTranslation = Constants.LIMELIGHT_HALF_POS.minus(piecePos);
    Rotation3d pieceRotation =
        new Rotation3d(
            0,
            -Math.atan(pieceTranslation.getY() * POS_TO_FOV_CONVERSION_Y),
            Math.atan(pieceTranslation.getX() * POS_TO_FOV_CONVERSION_X));
    Vector<N3> pieceVec = new Translation3d(1, 0, 0).rotateBy(pieceRotation).toVector();
    double maxDot = -1.0;
    BranchPositions closestBranch = null;
    for (BranchPositions branch : BranchPositions.values()) {
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
}
