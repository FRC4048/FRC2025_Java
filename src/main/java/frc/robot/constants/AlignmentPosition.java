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
      new Pose2d(
          3.2576000000000, 4.0614305050000, Rotation2d.fromRadians(3.1415926535898 - Math.PI)),
      Apriltag.EIGHTEEN,
      Apriltag.SEVEN),
  BRANCH_B(
      new Pose2d(
          3.2576000000000, 3.7328134950000, Rotation2d.fromRadians(3.1415926535898 - Math.PI)),
      Apriltag.EIGHTEEN,
      Apriltag.SEVEN),
  BRANCH_C(
      new Pose2d(
          3.8426981000607, 2.9769498582828, Rotation2d.fromRadians(4.1887902047864 - Math.PI)),
      Apriltag.SEVENTEEN,
      Apriltag.SIX),
  BRANCH_D(
      new Pose2d(
          4.1272887788364, 2.8126413532828, Rotation2d.fromRadians(4.1887902047864 - Math.PI)),
      Apriltag.SEVENTEEN,
      Apriltag.SIX),
  BRANCH_E(
      new Pose2d(
          5.0744349400607, 2.9414193532828, Rotation2d.fromRadians(5.2359877559830 - Math.PI)),
      Apriltag.TWENTY_TWO,
      Apriltag.ELEVEN),
  BRANCH_F(
      new Pose2d(
          5.3590256188364, 3.1057278582828, Rotation2d.fromRadians(5.2359877559830 - Math.PI)),
      Apriltag.TWENTY_TWO,
      Apriltag.ELEVEN),
  BRANCH_G(
      new Pose2d(
          5.7210736800000, 3.9903694950000, Rotation2d.fromRadians(6.2831853071796 - Math.PI)),
      Apriltag.TWENTY_ONE,
      Apriltag.TEN),
  BRANCH_H(
      new Pose2d(
          5.7210736800000, 4.3189865050000, Rotation2d.fromRadians(6.2831853071796 - Math.PI)),
      Apriltag.TWENTY_ONE,
      Apriltag.TEN),
  BRANCH_I(
      new Pose2d(
          5.1359755799393, 5.0748501417172, Rotation2d.fromRadians(7.3303828583762 - Math.PI)),
      Apriltag.TWENTY,
      Apriltag.NINE),
  BRANCH_J(
      new Pose2d(
          4.8513849011636, 5.2391586467172, Rotation2d.fromRadians(7.3303828583762 - Math.PI)),
      Apriltag.TWENTY,
      Apriltag.NINE),
  BRANCH_K(
      new Pose2d(
          3.9042387399393, 5.1103806467172, Rotation2d.fromRadians(8.3775804095728 - Math.PI)),
      Apriltag.NINETEEN,
      Apriltag.EIGHT),
  BRANCH_L(
      new Pose2d(
          3.6196480611636, 4.9460721417172, Rotation2d.fromRadians(8.3775804095728 - Math.PI)),
      Apriltag.NINETEEN,
      Apriltag.EIGHT);

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
