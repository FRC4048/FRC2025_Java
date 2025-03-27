package frc.robot.utils;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants.AlgaePositions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GamePieceLocateTest {

  @Test
  @Disabled(value="This test used the old limelight transforms (limelight on robot is different now)")
  void findAlgaePos() {
    double robotX = Apriltag.EIGHTEEN.getX() - 0.889;
    double robotY = Apriltag.EIGHTEEN.getY();
    double txDeg = 0; // degrees
    double dyDeg = -5; // degrees
    AlgaePositions algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertEquals(AlgaePositions.Algae_AB_LOW, algaePos);

    txDeg = 0; // degrees
    dyDeg = 20; // degrees
    algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertEquals(AlgaePositions.Algae_AB_HIGH, algaePos);

    txDeg = 10; // degrees
    dyDeg = 0; // degrees
    algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertEquals(AlgaePositions.Algae_CD_LOW, algaePos);

    txDeg = -10; // degrees
    dyDeg = 0; // degrees
    algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertEquals(AlgaePositions.Algae_KL_LOW, algaePos);
  }
}
