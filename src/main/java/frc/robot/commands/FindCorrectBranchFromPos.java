package frc.robot.commands;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.*;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.Constants;

public class FindCorrectBranchFromPos {
  public static final double POS_TO_FOV_CONVERSION_X = Math.tan(Constants.LIMELIGHT_HALF_FOV.getY()) / Constants.LIMELIGHT_HALF_POS.getY();
  public static final double POS_TO_FOV_CONVERSION_Y = Math.tan(Constants.LIMELIGHT_HALF_FOV.getX()) / Constants.LIMELIGHT_HALF_POS.getX();

    public BranchPositions FindBranch(Pose2d robotPos, Translation2d piecePos) {
    Pose3d cameraPos = new Pose3d(robotPos).transformBy(Constants.CAMERA_TO_ROBOT);
    Translation2d pieceTranslation = Constants.LIMELIGHT_HALF_POS.minus(piecePos);
    Rotation3d pieceRotation =
        new Rotation3d(
            0,
            Math.atan(
                pieceTranslation.getY()
                    * POS_TO_FOV_CONVERSION_Y),
            Math.atan(
                pieceTranslation.getX()
                    * POS_TO_FOV_CONVERSION_X));
    double minAngle = 2 * Math.PI;
    BranchPositions closestBranch = null;
    for (BranchPositions branch : BranchPositions.values()) {
      Translation3d branchTranslation = cameraPos.minus(branch.getPosition()).getTranslation();
      double dx = branchTranslation.getX();
      double dy = branchTranslation.getY();
      double dz = branchTranslation.getZ();
      Rotation3d branchRotation = new Rotation3d(0, Math.atan2(dz, Math.hypot(dx, dy)), Math.atan2(dy, dx));
      Rotation3d pieceToBranchRotation = branchRotation.minus(pieceRotation);
      if (pieceToBranchRotation.getAngle() < minAngle) {
        minAngle = pieceToBranchRotation.getAngle();
        closestBranch = branch;
      }
    }
    return closestBranch;
  }
}
