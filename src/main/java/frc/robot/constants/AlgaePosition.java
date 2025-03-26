package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;

public enum AlgaePosition {
  Algae_AB_LOW(
      new Pose3d(
          3.8337244850000, 4.0259000000000, 0.909311815, new Rotation3d(0, 0, 3.1415926535898))),
  Algae_AB_HIGH(
      new Pose3d(
          3.8337244850000, 4.0259000000000, 1.313978795, new Rotation3d(0, 0, 3.1415926535898))),
  Algae_CD_LOW(
      new Pose3d(
          4.1615306625000, 3.4581230455351, 0.909311815, new Rotation3d(0, 0, 4.1887902047864))),
  Algae_CD_HIGH(
      new Pose3d(
          4.1615306625000, 3.4581230455351, 1.313978795, new Rotation3d(0, 0, 4.1887902047864))),
  Algae_EF_LOW(
      new Pose3d(
          4.8171430175000, 3.4581230455351, 0.909311815, new Rotation3d(0, 0, 5.2359877559830))),
  Algae_EF_HIGH(
      new Pose3d(
          4.8171430175000, 3.4581230455351, 1.313978795, new Rotation3d(0, 0, 5.2359877559830))),
  Algae_GH_LOW(
      new Pose3d(
          5.1449491950000, 4.0259000000000, 0.909311815, new Rotation3d(0, 0, 6.2831853071796))),
  Algae_GH_HIGH(
      new Pose3d(
          5.1449491950000, 4.0259000000000, 1.313978795, new Rotation3d(0, 0, 6.2831853071796))),
  Algae_IJ_LOW(
      new Pose3d(
          4.8171430175000, 4.5936769544649, 0.909311815, new Rotation3d(0, 0, 7.3303828583762))),
  Algae_IJ_HIGH(
      new Pose3d(
          4.8171430175000, 4.5936769544649, 1.313978795, new Rotation3d(0, 0, 7.3303828583762))),
  Algae_KL_LOW(
      new Pose3d(
          4.1615306625000, 4.5936769544649, 0.909311815, new Rotation3d(0, 0, 8.3775804095728))),
  Algae_KL_HIGH(
      new Pose3d(
          4.1615306625000, 4.5936769544649, 1.313978795, new Rotation3d(0, 0, 8.3775804095728)));
  private final Pose3d position;

  AlgaePosition(Pose3d position) {
    this.position = position;
  }

  public Pose3d getPosition() {
    return position;
  }
}
