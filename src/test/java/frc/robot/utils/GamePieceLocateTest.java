package frc.robot.utils;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants.AlgaePositions;
import frc.robot.constants.AlignmentPosition;
import frc.robot.constants.BranchPositions;
import frc.robot.constants.ElevatorPosition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GamePieceLocateTest {

  @Test
  @Disabled(
      value = "This test used the old limelight transforms (limelight on robot is different now)")
  void findAlgaePos() {
    double robotX = Apriltag.EIGHTEEN.getX() - 0.889;
    double robotY = Apriltag.EIGHTEEN.getY();
    double txDeg = 0; // degrees
    double dyDeg = -5; // degrees
    GamePieceLocate.AlgaePositionMeasurement algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertNotNull(algaePos);
    assertEquals(AlgaePositions.Algae_AB_LOW, algaePos.algaePosition());

    txDeg = 0; // degrees
    dyDeg = 20; // degrees
    algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertNotNull(algaePos);
    assertEquals(AlgaePositions.Algae_AB_HIGH, algaePos.algaePosition());

    txDeg = 10; // degrees
    dyDeg = 0; // degrees
    algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertNotNull(algaePos);
    assertEquals(AlgaePositions.Algae_CD_LOW, algaePos.algaePosition());

    txDeg = -10; // degrees
    dyDeg = 0; // degrees
    algaePos =
        GamePieceLocate.findAlgaePos(new Pose2d(robotX, robotY, new Rotation2d(0)), txDeg, dyDeg);
    assertNotNull(algaePos);
    assertEquals(AlgaePositions.Algae_KL_LOW, algaePos.algaePosition());
  }

  @Test
  void calculateBranchLevel() {
    // Just Coral Tests
    // ================================================
    AlignmentPosition closest = AlignmentPosition.A;
    BranchPositions[] scoredCoral =
        new BranchPositions[] {
          BranchPositions.BRANCH_A_3, BranchPositions.BRANCH_B_2, BranchPositions.BRANCH_C_4
        };
    AlgaePositions[] scoredAlgae = new AlgaePositions[0];

    ElevatorPosition targetPosition =
        GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL4, targetPosition);

    closest = AlignmentPosition.A;
    scoredCoral =
        new BranchPositions[] {
          BranchPositions.BRANCH_A_4, BranchPositions.BRANCH_A_3, BranchPositions.BRANCH_C_4
        };
    scoredAlgae = new AlgaePositions[0];

    targetPosition = GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL2, targetPosition);
    // ================================================
    // Coral with algae tests
    // ================================================
    closest = AlignmentPosition.A;
    scoredCoral = new BranchPositions[] {BranchPositions.BRANCH_A_4, BranchPositions.BRANCH_C_4};
    scoredAlgae = new AlgaePositions[] {AlgaePositions.Algae_AB_LOW};

    targetPosition = GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL1, targetPosition);

    closest = AlignmentPosition.A;
    scoredCoral = new BranchPositions[] {BranchPositions.BRANCH_A_4, BranchPositions.BRANCH_C_4};
    scoredAlgae = new AlgaePositions[] {AlgaePositions.Algae_AB_HIGH};

    targetPosition = GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL2, targetPosition);

    closest = AlignmentPosition.A;
    scoredCoral = new BranchPositions[] {BranchPositions.BRANCH_A_3, BranchPositions.BRANCH_C_4};
    scoredAlgae = new AlgaePositions[] {AlgaePositions.Algae_AB_HIGH};

    targetPosition = GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL4, targetPosition);

    closest = AlignmentPosition.A;
    scoredCoral = new BranchPositions[] {BranchPositions.BRANCH_A_3, BranchPositions.BRANCH_A_4};
    scoredAlgae = new AlgaePositions[] {AlgaePositions.Algae_AB_LOW};

    targetPosition = GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL1, targetPosition);

    closest = AlignmentPosition.A;
    scoredCoral = new BranchPositions[] {BranchPositions.BRANCH_A_2, BranchPositions.BRANCH_A_4};
    scoredAlgae = new AlgaePositions[] {AlgaePositions.Algae_AB_HIGH};

    targetPosition = GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL1, targetPosition);

    closest = AlignmentPosition.A;
    scoredCoral = new BranchPositions[] {BranchPositions.BRANCH_A_4};
    scoredAlgae = new AlgaePositions[] {AlgaePositions.Algae_AB_HIGH, AlgaePositions.Algae_AB_LOW};

    targetPosition = GamePieceLocate.calculateBranchLevel(closest, scoredCoral, scoredAlgae);
    assertEquals(ElevatorPosition.LEVEL1, targetPosition);
  }
}
