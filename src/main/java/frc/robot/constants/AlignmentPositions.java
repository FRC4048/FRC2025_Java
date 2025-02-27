package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import java.util.Arrays;
import java.util.List;

public enum AlignmentPositions {
  BRANCH_A(new Pose2d(3.18849375, 4.0739863148, Rotation2d.fromDegrees(0))),
  BRANCH_B(new Pose2d(3.18849375, 3.7238597608, Rotation2d.fromDegrees(0))),
  BRANCH_C(new Pose2d(3.79133815262887167703, 2.93370262638828539811, Rotation2d.fromDegrees(60))),
  BRANCH_D(new Pose2d(4.09455664293237573925, 2.75863934938828539811, Rotation2d.fromDegrees(60))),
  BRANCH_E(new Pose2d(5.08027499562887167703, 2.88563934938828539811, Rotation2d.fromDegrees(120))),
  BRANCH_F(new Pose2d(5.38349348593237573925, 3.06070262638828539811, Rotation2d.fromDegrees(120))),
  BRANCH_G(new Pose2d(5.766367436, 3.9778597608, Rotation2d.fromDegrees(180))),
  BRANCH_H(new Pose2d(5.766367436, 4.3279863148, Rotation2d.fromDegrees(180))),
  BRANCH_I(new Pose2d(5.16352303337112832297, 5.11814344921171460189, Rotation2d.fromDegrees(240))),
  BRANCH_J(new Pose2d(4.86030454306762426075, 5.29320672621171460189, Rotation2d.fromDegrees(240))),
  BRANCH_K(new Pose2d(3.87458619037112832297, 5.16620672621171460189, Rotation2d.fromDegrees(300))),
  BRANCH_L(
      new Pose2d(
          3.57136770006762426075,
          4.99114344921171460189,
          Rotation2d.fromDegrees(300))); // TODO: ALL PLACEHOLDERS

  private final Pose2d position;

  AlignmentPositions(Pose2d position) {
    this.position = position;
  }

  public Pose2d getPosition() {
    return position;
  }

  public static Pose2d getClosest(Pose2d currentPosition) {
    List<Pose2d> poseList =
        Arrays.stream(AlignmentPositions.values()).map(AlignmentPositions::getPosition).toList();
    return currentPosition.nearest(poseList);
  }
}
