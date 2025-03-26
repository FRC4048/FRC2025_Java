/** Technically not used now, mainly just for checking if the math is right */
package frc.robot.constants;

import edu.wpi.first.math.geometry.*;

// TODO: change all of these later to the correct values
public enum BranchPositions {
  BRANCH_A_2(
      AlignmentPosition.A,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          3.8337244850000, 4.1902085050000, 0.706978325, new Rotation3d(0, 0, 3.1415926535898))),
  BRANCH_A_3(
      AlignmentPosition.A,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          3.8337244850000, 4.1902085050000, 1.111645305, new Rotation3d(0, 0, 3.1415926535898))),
  BRANCH_A_4(
      AlignmentPosition.A,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          3.6896079100000, 4.1902085050000, 1.736182305, new Rotation3d(0, 0, 3.1415926535898))),
  BRANCH_B_2(
      AlignmentPosition.B,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          3.8337244850000, 3.8615914950000, 0.706978325, new Rotation3d(0, 0, 3.1415926535898))),
  BRANCH_B_3(
      AlignmentPosition.B,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          3.8337244850000, 3.8615914950000, 1.111645305, new Rotation3d(0, 0, 3.1415926535898))),
  BRANCH_B_4(
      AlignmentPosition.B,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          3.6896079100000, 3.8615914950000, 1.736182305, new Rotation3d(0, 0, 3.1415926535898))),
  BRANCH_C_2(
      AlignmentPosition.C,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.0192353231122, 3.5402772980351, 0.706978325, new Rotation3d(0, 0, 4.1887902047864))),
  BRANCH_C_3(
      AlignmentPosition.C,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.0192353231122, 3.5402772980351, 1.111645305, new Rotation3d(0, 0, 4.1887902047864))),
  BRANCH_C_4(
      AlignmentPosition.C,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          3.9471770356122, 3.4154686829787, 1.736182305, new Rotation3d(0, 0, 4.1887902047864))),
  BRANCH_D_2(
      AlignmentPosition.D,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.3038260018878, 3.3759687930351, 0.706978325, new Rotation3d(0, 0, 4.1887902047864))),
  BRANCH_D_3(
      AlignmentPosition.D,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.3038260018878, 3.3759687930351, 1.111645305, new Rotation3d(0, 0, 4.1887902047864))),
  BRANCH_D_4(
      AlignmentPosition.D,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          4.2317677143878, 3.2511601779787, 1.736182305, new Rotation3d(0, 0, 4.1887902047864))),
  BRANCH_E_2(
      AlignmentPosition.E,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.6748476781122, 3.3759687930351, 0.706978325, new Rotation3d(0, 0, 5.2359877559830))),
  BRANCH_E_3(
      AlignmentPosition.E,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.6748476781122, 3.3759687930351, 1.111645305, new Rotation3d(0, 0, 5.2359877559830))),
  BRANCH_E_4(
      AlignmentPosition.E,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          4.7469059656122, 3.2511601779787, 1.736182305, new Rotation3d(0, 0, 5.2359877559830))),
  BRANCH_F_2(
      AlignmentPosition.F,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.9594383568878, 3.5402772980351, 0.706978325, new Rotation3d(0, 0, 5.2359877559830))),
  BRANCH_F_3(
      AlignmentPosition.F,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.9594383568878, 3.5402772980351, 1.111645305, new Rotation3d(0, 0, 5.2359877559830))),
  BRANCH_F_4(
      AlignmentPosition.F,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          5.0314966443878, 3.4154686829787, 1.736182305, new Rotation3d(0, 0, 5.2359877559830))),
  BRANCH_G_2(
      AlignmentPosition.G,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          5.1449491950000, 3.8615914950000, 0.706978325, new Rotation3d(0, 0, 6.2831853071796))),
  BRANCH_G_3(
      AlignmentPosition.G,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          5.1449491950000, 3.8615914950000, 1.111645305, new Rotation3d(0, 0, 6.2831853071796))),
  BRANCH_G_4(
      AlignmentPosition.G,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          5.2890657700000, 3.8615914950000, 1.736182305, new Rotation3d(0, 0, 6.2831853071796))),
  BRANCH_H_2(
      AlignmentPosition.H,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          5.1449491950000, 4.1902085050000, 0.706978325, new Rotation3d(0, 0, 6.2831853071796))),
  BRANCH_H_3(
      AlignmentPosition.H,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          5.1449491950000, 4.1902085050000, 1.111645305, new Rotation3d(0, 0, 6.2831853071796))),
  BRANCH_H_4(
      AlignmentPosition.H,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          5.2890657700000, 4.1902085050000, 1.736182305, new Rotation3d(0, 0, 6.2831853071796))),
  BRANCH_I_2(
      AlignmentPosition.I,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.9594383568878, 4.5115227019649, 0.706978325, new Rotation3d(0, 0, 7.3303828583762))),
  BRANCH_I_3(
      AlignmentPosition.I,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.9594383568878, 4.5115227019649, 1.111645305, new Rotation3d(0, 0, 7.3303828583762))),
  BRANCH_I_4(
      AlignmentPosition.I,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          5.0314966443878, 4.6363313170214, 1.736182305, new Rotation3d(0, 0, 7.3303828583762))),
  BRANCH_J_2(
      AlignmentPosition.J,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.6748476781122, 4.6758312069649, 0.706978325, new Rotation3d(0, 0, 7.3303828583762))),
  BRANCH_J_3(
      AlignmentPosition.J,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.6748476781122, 4.6758312069649, 1.111645305, new Rotation3d(0, 0, 7.3303828583762))),
  BRANCH_J_4(
      AlignmentPosition.J,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          4.7469059656122, 4.8006398220214, 1.736182305, new Rotation3d(0, 0, 7.3303828583762))),
  BRANCH_K_2(
      AlignmentPosition.K,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.3038260018878, 4.6758312069649, 0.706978325, new Rotation3d(0, 0, 8.3775804095728))),
  BRANCH_K_3(
      AlignmentPosition.K,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.3038260018878, 4.6758312069649, 1.111645305, new Rotation3d(0, 0, 8.3775804095728))),
  BRANCH_K_4(
      AlignmentPosition.K,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          4.2317677143878, 4.8006398220214, 1.736182305, new Rotation3d(0, 0, 8.3775804095728))),
  BRANCH_L_2(
      AlignmentPosition.L,
      ElevatorPosition.LEVEL2,
      new Pose3d(
          4.0192353231122, 4.5115227019649, 0.706978325, new Rotation3d(0, 0, 8.3775804095728))),
  BRANCH_L_3(
      AlignmentPosition.L,
      ElevatorPosition.LEVEL3,
      new Pose3d(
          4.0192353231122, 4.5115227019649, 1.111645305, new Rotation3d(0, 0, 8.3775804095728))),
  BRANCH_L_4(
      AlignmentPosition.L,
      ElevatorPosition.LEVEL4,
      new Pose3d(
          3.9471770356122, 4.6363313170214, 1.736182305, new Rotation3d(0, 0, 8.3775804095728)));

  private final AlignmentPosition trunk;
  private final ElevatorPosition elevatorPosition;
  private final Pose3d position;

  BranchPositions(AlignmentPosition trunk, ElevatorPosition elevatorPosition, Pose3d position) {
    this.trunk = trunk;
    this.elevatorPosition = elevatorPosition;
    this.position = position;
  }

  public Pose3d getPosition() {
    return position;
  }

  public AlignmentPosition getTrunk() {
    return trunk;
  }

  public ElevatorPosition getElevatorLevel() {
    return elevatorPosition;
  }
}
