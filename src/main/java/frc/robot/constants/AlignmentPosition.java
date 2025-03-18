package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Robot;
import frc.robot.utils.Apriltag;
import java.util.Optional;

public enum AlignmentPosition {
  BRANCH_A(
      new Pose2d(-0.3424, 4.068154, Rotation2d.fromRadians(3.141592654)),
      Apriltag.EIGHTEEN,
      Apriltag.SEVEN
  ),
  BRANCH_B(
      new Pose2d(-0.3424, 3.739154, Rotation2d.fromRadians(3.141592654)),
      Apriltag.EIGHTEEN,
      Apriltag.SEVEN
  ),
  BRANCH_C(
      new Pose2d(2.021430073, -0.09755978616, Rotation2d.fromRadians(4.188790205)),
      Apriltag.SEVENTEEN,
      Apriltag.SIX
  ),
  BRANCH_D(
      new Pose2d(2.30635243, -0.2620597862, Rotation2d.fromRadians(4.188790205)),
      Apriltag.SEVENTEEN,
      Apriltag.SIX
  ),
  BRANCH_E(
      new Pose2d(6.810959073, -0.1332797862, Rotation2d.fromRadians(5.235987756)),
      Apriltag.TWENTY_TWO,
      Apriltag.ELEVEN
  ),
  BRANCH_F(
      new Pose2d(7.09588143, 0.03122021384, Rotation2d.fromRadians(5.235987756)),
      Apriltag.TWENTY_TWO,
      Apriltag.ELEVEN
  ),
  BRANCH_G(
      new Pose2d(9.236658, 3.996714, Rotation2d.fromRadians(6.283185307)),
      Apriltag.TWENTY_ONE,
      Apriltag.TEN
  ),
  BRANCH_H(
      new Pose2d(9.236658, 4.325714, Rotation2d.fromRadians(6.283185307)),
      Apriltag.TWENTY_ONE,
      Apriltag.TEN
  ),
  BRANCH_I(
      new Pose2d(6.872827927, 8.162427786, Rotation2d.fromRadians(7.330382858)),
      Apriltag.TWENTY,
      Apriltag.NINE
  ),
  BRANCH_J(
      new Pose2d(6.58790557, 8.326927786, Rotation2d.fromRadians(7.330382858)),
      Apriltag.TWENTY,
      Apriltag.NINE
  ),
  BRANCH_K(
      new Pose2d(2.083298927, 8.198147786, Rotation2d.fromRadians(8.37758041)),
      Apriltag.NINETEEN,
      Apriltag.EIGHT
  ),
  BRANCH_L(
      new Pose2d(1.79837657, 8.033647786, Rotation2d.fromRadians(8.37758041)),
      Apriltag.NINETEEN,
      Apriltag.EIGHT
  );

  private final Pose2d position;
  private final Apriltag blueTag;
  private final Apriltag redTag;

  AlignmentPosition(Pose2d position, Apriltag blueTag, Apriltag redTag) {
    this.position = position;
    this.blueTag = blueTag;
    this.redTag = redTag;
  }

  public Pose2d getPosition() {
    return position;
  }

  public static AlignmentPosition getClosest(Translation2d currentPosition) {
    int closestIndex = 0;
    double closestDistance = values()[0].position.getTranslation().getDistance(currentPosition);
    for (int i = 1; i < values().length; i++) {
      double distance = values()[i].position.getTranslation().getDistance(currentPosition);
      if (distance < closestDistance) {
        closestDistance = distance;
        closestIndex = i;
      }
    }
    return values()[closestIndex];
  }

  public Apriltag getBlueTag() {
    return blueTag;
  }

  public Apriltag getRedTag() {
    return redTag;
  }

  public Apriltag getTag() {
    Optional<DriverStation.Alliance> allianceColor = Robot.getAllianceColor();
    return allianceColor
        .map(alliance -> alliance == DriverStation.Alliance.Blue ? getBlueTag() : getRedTag())
        .orElse(null);
  }
}
