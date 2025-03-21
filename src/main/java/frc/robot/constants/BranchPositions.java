/** Technically not used now, mainly just for checking if the math is right */
package frc.robot.constants;

import edu.wpi.first.math.geometry.*;

// TODO: change all of these later to the correct values
public enum BranchPositions {
  BRANCH_A_1(new Pose3d(3.6576, 4.196934, 0, new Rotation3d(0, 0, 3.141592654)));
  //  BRANCH_B(new Pose2d(3.6576, 3.867934, Rotation2d.fromRadians(3.141592654))),
  //  BRANCH_C(new Pose2d(3.909903321, 3.430931829, Rotation2d.fromRadians(4.188790205))),
  //  BRANCH_D(new Pose2d(4.194825679, 3.266431829, Rotation2d.fromRadians(4.188790205))),
  //  BRANCH_E(new Pose2d(4.699432321, 3.266431829, Rotation2d.fromRadians(5.235987756))),
  //  BRANCH_F(new Pose2d(4.984354679, 3.430931829, Rotation2d.fromRadians(5.235987756))),
  //  BRANCH_G(new Pose2d(5.236658, 3.867934, Rotation2d.fromRadians(6.283185307))),
  //  BRANCH_H(new Pose2d(5.236658, 4.196934, Rotation2d.fromRadians(6.283185307))),
  //  BRANCH_I(new Pose2d(4.984354679, 4.633936171, Rotation2d.fromRadians(7.330382858))),
  //  BRANCH_J(new Pose2d(4.699432321, 4.798436171, Rotation2d.fromRadians(7.330382858))),
  //  BRANCH_K(new Pose2d(4.194825679, 4.798436171, Rotation2d.fromRadians(8.37758041))),
  //  BRANCH_L(new Pose2d(3.909903321, 4.633936171, Rotation2d.fromRadians(8.37758041)));

  private final Pose3d position;

  BranchPositions(Pose3d position) {
    this.position = position;
  }

  public Pose3d getPosition() {
    return position;
  }
}
