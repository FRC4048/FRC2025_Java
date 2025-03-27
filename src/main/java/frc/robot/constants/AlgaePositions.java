package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;

public enum AlgaePositions {
  Algae_AB_LOW(
      AlignmentPosition.A,
      AlignmentPosition.B,
      false,
      new Pose3d(
          3.8337244850000, 4.0259000000000, 0.909311815, new Rotation3d(0, 0, 3.1415926535898))),
  Algae_AB_HIGH(
      AlignmentPosition.A,
      AlignmentPosition.B,
      true,
      new Pose3d(
          3.8337244850000, 4.0259000000000, 1.313978795, new Rotation3d(0, 0, 3.1415926535898))),
  Algae_CD_LOW(
      AlignmentPosition.C,
      AlignmentPosition.D,
      false,
      new Pose3d(
          4.1615306625000, 3.4581230455351, 0.909311815, new Rotation3d(0, 0, 4.1887902047864))),
  Algae_CD_HIGH(
      AlignmentPosition.C,
      AlignmentPosition.D,
      true,
      new Pose3d(
          4.1615306625000, 3.4581230455351, 1.313978795, new Rotation3d(0, 0, 4.1887902047864))),
  Algae_EF_LOW(
      AlignmentPosition.E,
      AlignmentPosition.F,
      false,
      new Pose3d(
          4.8171430175000, 3.4581230455351, 0.909311815, new Rotation3d(0, 0, 5.2359877559830))),
  Algae_EF_HIGH(
      AlignmentPosition.E,
      AlignmentPosition.F,
      true,
      new Pose3d(
          4.8171430175000, 3.4581230455351, 1.313978795, new Rotation3d(0, 0, 5.2359877559830))),
  Algae_GH_LOW(
      AlignmentPosition.G,
      AlignmentPosition.H,
      false,
      new Pose3d(
          5.1449491950000, 4.0259000000000, 0.909311815, new Rotation3d(0, 0, 6.2831853071796))),
  Algae_GH_HIGH(
      AlignmentPosition.H,
      AlignmentPosition.H,
      true,
      new Pose3d(
          5.1449491950000, 4.0259000000000, 1.313978795, new Rotation3d(0, 0, 6.2831853071796))),
  Algae_IJ_LOW(
      AlignmentPosition.I,
      AlignmentPosition.J,
      false,
      new Pose3d(
          4.8171430175000, 4.5936769544649, 0.909311815, new Rotation3d(0, 0, 7.3303828583762))),
  Algae_IJ_HIGH(
      AlignmentPosition.I,
      AlignmentPosition.J,
      true,
      new Pose3d(
          4.8171430175000, 4.5936769544649, 1.313978795, new Rotation3d(0, 0, 7.3303828583762))),
  Algae_KL_LOW(
      AlignmentPosition.K,
      AlignmentPosition.L,
      false,
      new Pose3d(
          4.1615306625000, 4.5936769544649, 0.909311815, new Rotation3d(0, 0, 8.3775804095728))),
  Algae_KL_HIGH(
      AlignmentPosition.K,
      AlignmentPosition.L,
      true,
      new Pose3d(
          4.1615306625000, 4.5936769544649, 1.313978795, new Rotation3d(0, 0, 8.3775804095728)));
  private final AlignmentPosition al1;
  private final AlignmentPosition al2;
  private final boolean high;
  private final Pose3d position;

  AlgaePositions(AlignmentPosition al1, AlignmentPosition al2, boolean high, Pose3d position) {
    this.al1 = al1;
    this.al2 = al2;
    this.high = high;
    this.position = position;
  }

  public Pose3d getPosition() {
    return position;
  }

  public AlignmentPosition getAlignmentPosition1() {
    return al1;
  }

  public AlignmentPosition getAlignmentPosition2() {
    return al2;
  }

  public boolean isHigh() {
    return high;
  }
}
