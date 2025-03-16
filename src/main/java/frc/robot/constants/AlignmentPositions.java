package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import java.util.Arrays;
import java.util.List;

public enum AlignmentPositions {
  BRANCH_A(new Pose2d(-0.3424, 4.068154, Rotation2d.fromRadians(3.141592654))), 
  BRANCH_B(new Pose2d(-0.3424, 3.739154, Rotation2d.fromRadians(3.141592654))),
  BRANCH_C(new Pose2d(2.021430073, -0.09755978616, Rotation2d.fromRadians(4.188790205))),
  BRANCH_D(new Pose2d(2.30635243, -0.2620597862, Rotation2d.fromRadians(4.188790205))),
  BRANCH_E(new Pose2d(6.810959073, -0.1332797862, Rotation2d.fromRadians(5.235987756))),
  BRANCH_F(new Pose2d(7.09588143, 0.03122021384, Rotation2d.fromRadians(5.235987756))),
  BRANCH_G(new Pose2d(9.236658, 3.996714, Rotation2d.fromRadians(6.283185307))),
  BRANCH_H(new Pose2d(9.236658, 4.325714, Rotation2d.fromRadians(6.283185307))),
  BRANCH_I(new Pose2d(6.872827927, 8.162427786, Rotation2d.fromRadians(7.330382858))),
  BRANCH_J(new Pose2d(6.58790557, 8.326927786, Rotation2d.fromRadians(7.330382858))),
  BRANCH_K(new Pose2d(2.083298927, 8.198147786, Rotation2d.fromRadians(8.37758041))),
  BRANCH_L(new Pose2d(1.79837657, 8.033647786, Rotation2d.fromRadians(8.37758041)));

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
