package frc.robot.utils;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants.AlgaePositions;
import org.junit.jupiter.api.Test;

class GamePieceLocateTest {

  @Test
  void findAlgaePos() {
    double robotX = Apriltag.EIGHTEEN.getX() - 0.889;
    double robotY = Apriltag.EIGHTEEN.getY();
    double visionX = 5; // degrees
    double visionY = -1; // degrees
    AlgaePositions algaePos =
        GamePieceLocate.findAlgaePos(
            new Pose2d(robotX, robotY, new Rotation2d(0)), visionX, visionY);
    assertEquals(AlgaePositions.Algae_AB_LOW, algaePos);
  }
}
