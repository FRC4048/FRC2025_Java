package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

// Technically not used now, mainly just for checking if the math is right
public enum BranchPositions {
  CENTER(new Pose2d(4.477430593, 4.0259230378, Rotation2d.fromDegrees(0.0))),
  CENTER_A_B(new Pose2d(3.64569375, 4.0259230378, Rotation2d.fromDegrees(0))),
  CENTER_C_D(new Pose2d(4.0615621715, 3.30561780249853074741, Rotation2d.fromDegrees(60))),
  CENTER_E_F(new Pose2d(4.8932990145, 3.30561780249853074741, Rotation2d.fromDegrees(120))),
  CENTER_G_H(new Pose2d(5.309167436, 4.0259230378, Rotation2d.fromDegrees(180))),
  CENTER_I_J(new Pose2d(4.8932990145, 4.74622827310146925259, Rotation2d.fromDegrees(240))),
  CENTER_K_L(new Pose2d(4.0615621715, 4.74622827310146925259, Rotation2d.fromDegrees(300))),
  BRANCH_A(new Pose2d(3.64569375, 4.2009863148, Rotation2d.fromDegrees(0))),
  BRANCH_B(new Pose2d(3.64569375, 3.8508597608, Rotation2d.fromDegrees(0))),
  BRANCH_C(new Pose2d(3.90995292634824796889, 3.39314944099853074741, Rotation2d.fromDegrees(60))),
  BRANCH_D(new Pose2d(4.21317141665175203111, 3.21808616399853074741, Rotation2d.fromDegrees(60))),
  BRANCH_E(new Pose2d(4.74168976934824796889, 3.21808616399853074741, Rotation2d.fromDegrees(120))),
  BRANCH_F(new Pose2d(5.04490825965175203111, 3.39314944099853074741, Rotation2d.fromDegrees(120))),
  BRANCH_G(new Pose2d(5.309167436, 3.8508597608, Rotation2d.fromDegrees(180))),
  BRANCH_H(new Pose2d(5.309167436, 4.2009863148, Rotation2d.fromDegrees(180))),
  BRANCH_I(new Pose2d(5.04490825965175203111, 4.65869663460146925259, Rotation2d.fromDegrees(240))),
  BRANCH_J(new Pose2d(4.74168976934824796889, 4.83375991160146925259, Rotation2d.fromDegrees(240))),
  BRANCH_K(new Pose2d(4.21317141665175203111, 4.83375991160146925259, Rotation2d.fromDegrees(300))),
  BRANCH_L(new Pose2d(3.90995292634824796889, 4.65869663460146925259, Rotation2d.fromDegrees(300)));

  private final Pose2d position;

  BranchPositions(Pose2d position) {
    this.position = position;
  }

  public Pose2d getPosition() {
    return position;
  }
}
