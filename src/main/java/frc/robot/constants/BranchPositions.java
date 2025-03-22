/** Technically not used now, mainly just for checking if the math is right */
package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public enum BranchPositions {
  CENTER(new Pose2d(0, 0, Rotation2d.fromRadians(0))), // TODO: add number for center of the reef
  CENTER_A_B(new Pose2d(3.6576, 4.032434, Rotation2d.fromRadians(3.1415926535898))),
  CENTER_C_D(new Pose2d(4.0523645, 3.348681829, Rotation2d.fromRadians(4.1887902047864))),
  CENTER_E_F(new Pose2d(4.8418935, 3.348681829, Rotation2d.fromRadians(5.2359877559830))),
  CENTER_G_H(new Pose2d(5.236658, 4.032434, Rotation2d.fromRadians(6.2831853071796))),
  CENTER_I_J(new Pose2d(4.8418935, 4.716186171, Rotation2d.fromRadians(7.3303828583762))),
  CENTER_K_L(new Pose2d(4.0523645, 4.716186171, Rotation2d.fromRadians(8.3775804095728))),

  BRANCH_A(new Pose2d(3.6576000000000, 4.1902085050000, Rotation2d.fromRadians(3.1415926535898))),
  BRANCH_B(new Pose2d(3.6576000000000, 3.8615914950000, Rotation2d.fromRadians(3.1415926535898))),
  BRANCH_C(new Pose2d(3.9311730806122, 3.3877490197966, Rotation2d.fromRadians(4.1887902047864))),
  BRANCH_D(new Pose2d(4.2157637593878, 3.2234405147966, Rotation2d.fromRadians(4.1887902047864))),
  BRANCH_E(new Pose2d(4.7629099206122, 3.2234405147966, Rotation2d.fromRadians(5.2359877559830))),
  BRANCH_F(new Pose2d(5.0475005993878, 3.3877490197966, Rotation2d.fromRadians(5.2359877559830))),
  BRANCH_G(new Pose2d(5.3210736800000, 3.8615914950000, Rotation2d.fromRadians(6.2831853071796))),
  BRANCH_H(new Pose2d(5.3210736800000, 4.1902085050000, Rotation2d.fromRadians(6.2831853071796))),
  BRANCH_I(new Pose2d(5.0475005993878, 4.6640509802034, Rotation2d.fromRadians(7.3303828583762))),
  BRANCH_J(new Pose2d(4.7629099206122, 4.8283594852034, Rotation2d.fromRadians(7.3303828583762))),
  BRANCH_K(new Pose2d(4.2157637593878, 4.8283594852034, Rotation2d.fromRadians(8.3775804095728))),
  BRANCH_L(new Pose2d(3.9311730806122, 4.6640509802034, Rotation2d.fromRadians(8.3775804095728)));

  private final Pose2d position;

  BranchPositions(Pose2d position) {
    this.position = position;
  }

  public Pose2d getPosition() {
    return position;
  }
}
