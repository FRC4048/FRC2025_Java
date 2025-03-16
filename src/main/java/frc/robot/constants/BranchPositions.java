/** Technically not used now, mainly just for checking if the math is right */
package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public enum BranchPositions {
  CENTER(new Pose2d(0, 0, Rotation2d.fromRadians(0))), //TODO: add number for center of the reef
  CENTER_A_B(new Pose2d(3.6576, 4.032434, Rotation2d.fromRadians(3.141592654))),
  CENTER_C_D(new Pose2d(4.0523645, 3.348681829, Rotation2d.fromRadians(4.188790205))), 
  CENTER_E_F(new Pose2d(4.8418935, 3.348681829, Rotation2d.fromRadians(5.235987756))), 
  CENTER_G_H(new Pose2d(5.236658, 4.032434, Rotation2d.fromRadians(6.283185307))),
  CENTER_I_J(new Pose2d(4.8418935, 4.716186171, Rotation2d.fromRadians(7.330382858))),
  CENTER_K_L(new Pose2d(4.0523645, 4.716186171, Rotation2d.fromRadians(8.37758041))),

  BRANCH_A(new Pose2d(3.6576, 4.196934, Rotation2d.fromRadians(3.141592654))),
  BRANCH_B(new Pose2d(3.6576, 3.867934, Rotation2d.fromRadians(3.141592654))),
  BRANCH_C(new Pose2d(3.909903321, 3.430931829, Rotation2d.fromRadians(4.188790205))),
  BRANCH_D(new Pose2d(4.194825679, 3.266431829, Rotation2d.fromRadians(4.188790205))),
  BRANCH_E(new Pose2d(4.699432321, 3.266431829, Rotation2d.fromRadians(5.235987756))),
  BRANCH_F(new Pose2d(4.984354679, 3.430931829, Rotation2d.fromRadians(5.235987756))),
  BRANCH_G(new Pose2d(5.236658, 3.867934, Rotation2d.fromRadians(6.283185307))),
  BRANCH_H(new Pose2d(5.236658, 4.196934, Rotation2d.fromRadians(6.283185307))),
  BRANCH_I(new Pose2d(4.984354679, 4.633936171, Rotation2d.fromRadians(7.330382858))),
  BRANCH_J(new Pose2d(4.699432321, 4.798436171, Rotation2d.fromRadians(7.330382858))),
  BRANCH_K(new Pose2d(4.194825679, 4.798436171, Rotation2d.fromRadians(8.37758041))),
  BRANCH_L(new Pose2d(3.909903321, 4.633936171, Rotation2d.fromRadians(8.37758041)));

  private final Pose2d position;

  BranchPositions(Pose2d position) {
    this.position = position;
  }

  public Pose2d getPosition() {
    return position;
  }
}
