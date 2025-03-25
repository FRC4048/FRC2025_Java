package frc.robot.utils;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants.AlgaePositions;
import org.junit.jupiter.api.Test;

class GamePieceLocateTest {
  // These are wrong I think
  @Test
  void findAlgaePos() {
    double x = 0.31119220563058896357;
    double y = 0.06719517620178168871;
    AlgaePositions algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(2.614524485, 4.0259, new Rotation2d(0)), x, y);
    assertEquals(5.144949195, algaePos.getPosition().getX());
    assertEquals(4.0259, algaePos.getPosition().getY());
  }
}
