package frc.robot.commands;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.*;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;

public class FindCorrectBranchFromPos {
  public BranchPositions FindBranch(Pose2d robotPos, Translation2d piecePos) {
    Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    Translation2d pieceTranslation = piecePos.minus(Constants.LIMELIGHT_MAX_POS.div(2));
    Rotation3d pieceRotation =
        new Rotation3d(
            0,
            pieceTranslation.getY() * Constants.LIMELIGHT_MAX_FOV.getY() / Constants.LIMELIGHT_MAX_POS.getY(),
            pieceTranslation.getX() * Constants.LIMELIGHT_MAX_FOV.getX() / Constants.LIMELIGHT_MAX_POS.getX());
    double absAngle = 2 * Math.PI;
    BranchPositions closestBranch = null;
    for (BranchPositions branch : BranchPositions.values()) {
      Translation3d branchTranslation = cameraPos.minus(branch.getPosition()).getTranslation();
      Rotation3d branchRotation =
          new Rotation3d(
              VecBuilder.fill(
                  branchTranslation.getX(), branchTranslation.getY(), branchTranslation.getZ()));
      Rotation3d pieceToBranchRotation = branchRotation.minus(pieceRotation);
      if (pieceToBranchRotation.getAngle() < absAngle) {
        absAngle = pieceToBranchRotation.getAngle();
        closestBranch = branch;
      }
    }
    return closestBranch;
  }
}
