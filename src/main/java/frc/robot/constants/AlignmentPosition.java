package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Robot;
import frc.robot.autochooser.FieldLocation;
import frc.robot.utils.Apriltag;
import java.util.Optional;

public enum AlignmentPosition {
  BRANCH_A(
      new Pose2d(3.2067500000000, 4.0614305050000, Rotation2d.fromDegrees(0)),
      Apriltag.EIGHTEEN,
      Apriltag.SEVEN),
  BRANCH_B(
      new Pose2d(3.2067500000000, 3.7328134950000, Rotation2d.fromRadians(0)),
      Apriltag.EIGHTEEN,
      Apriltag.SEVEN),
  BRANCH_C(
      new Pose2d(3.8426981000607, 2.9769498582828, Rotation2d.fromDegrees(60)),
      Apriltag.SEVENTEEN,
      Apriltag.SIX),
  BRANCH_D(
      new Pose2d(4.1272887788364, 2.8126413532828, Rotation2d.fromDegrees(60)),
      Apriltag.SEVENTEEN,
      Apriltag.SIX),
  BRANCH_E(
      new Pose2d(5.0994349400607, 2.8981180830936, Rotation2d.fromDegrees(120)),
      Apriltag.TWENTY_TWO,
      Apriltag.ELEVEN),
  BRANCH_F(
      new Pose2d(5.3590256188364, 3.1057278582828, Rotation2d.fromDegrees(120)),
      Apriltag.TWENTY_TWO,
      Apriltag.ELEVEN),
  BRANCH_G(
      new Pose2d(5.7210736800000, 3.9903694950000, Rotation2d.fromDegrees(180)),
      Apriltag.TWENTY_ONE,
      Apriltag.TEN),
  BRANCH_H(
      new Pose2d(5.7210736800000, 4.3189865050000, Rotation2d.fromDegrees(180)),
      Apriltag.TWENTY_ONE,
      Apriltag.TEN),
  BRANCH_I(
      new Pose2d(5.1359755799393, 5.0748501417172, Rotation2d.fromDegrees(240)),
      Apriltag.TWENTY,
      Apriltag.NINE),
  BRANCH_J(
      new Pose2d(4.8513849011636, 5.2391586467172, Rotation2d.fromDegrees(240)),
      Apriltag.TWENTY,
      Apriltag.NINE),
  BRANCH_K(
      new Pose2d(3.9042387399393, 5.1103806467172, Rotation2d.fromDegrees(300)),
      Apriltag.NINETEEN,
      Apriltag.EIGHT),
  BRANCH_L(
      new Pose2d(3.6196480611636, 4.9460721417172, Rotation2d.fromDegrees(300)),
      Apriltag.NINETEEN,
      Apriltag.EIGHT);

  private final Pose2d bluePosition;
  private final Pose2d redPosition;
  private final Apriltag blueTag;
  private final Apriltag redTag;

  AlignmentPosition(Pose2d position, Apriltag blueTag, Apriltag redTag) {
    this.bluePosition = position;
    Rotation2d redRotation = Rotation2d.fromRadians(Math.PI - position.getRotation().getRadians());
    double redX = FieldLocation.LENGTH_OF_FIELD - bluePosition.getX();
    double redY = FieldLocation.HEIGHT_OF_FIELD - bluePosition.getY();
    this.redPosition = new Pose2d(redX, redY, redRotation);
    this.blueTag = blueTag;
    this.redTag = redTag;
  }

  public Pose2d getBluePosition() {
    return bluePosition;
  }

  public Pose2d getRedPosition() {
    return redPosition;
  }

  public Pose2d getPosition() {
    Optional<DriverStation.Alliance> allianceColor = Robot.getAllianceColor();
    return allianceColor
        .map(
            alliance ->
                alliance == DriverStation.Alliance.Blue ? getBluePosition() : getRedPosition())
        .orElse(new Pose2d());
  }

  public static AlignmentPosition getClosest(Translation2d currentPosition) {
    int closestIndex = 0;
    double closestDistance = values()[0].getPosition().getTranslation().getDistance(currentPosition);
    for (int i = 1; i < values().length; i++) {
      double distance = values()[i].getPosition().getTranslation().getDistance(currentPosition);
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
