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
  // spotless:off Although this makes it harder to read it makes it easy to copy and paste from sheets

  A(new Pose2d(3.2076000000000, 4.0614305050000, Rotation2d.fromDegrees(0)), Apriltag.EIGHTEEN, Apriltag.SEVEN),

  B(new Pose2d(3.2076000000000, 3.7328134950000, Rotation2d.fromRadians(0)), Apriltag.EIGHTEEN, Apriltag.SEVEN),

  C(new Pose2d(3.8176981000607, 2.9336485880936, Rotation2d.fromDegrees(60)), Apriltag.SEVENTEEN, Apriltag.EIGHT),

  D(new Pose2d(4.1022887788364, 2.7693400830936, Rotation2d.fromDegrees(60)), Apriltag.SEVENTEEN, Apriltag.EIGHT),

  E(new Pose2d(5.0994349400607, 2.8981180830936, Rotation2d.fromDegrees(120)), Apriltag.TWENTY_TWO, Apriltag.NINE),

  F(new Pose2d(5.3840256188364, 3.0624265880936, Rotation2d.fromDegrees(120)), Apriltag.TWENTY_TWO, Apriltag.NINE),

  G(new Pose2d(5.7710736800000, 3.9903694950000, Rotation2d.fromDegrees(180)), Apriltag.TWENTY_ONE, Apriltag.TEN),

  H(new Pose2d(5.7710736800000, 4.3189865050000, Rotation2d.fromDegrees(180)), Apriltag.TWENTY_ONE, Apriltag.TEN),

  I(new Pose2d(5.1609755799393, 5.1181514119064, Rotation2d.fromDegrees(240)), Apriltag.TWENTY, Apriltag.ELEVEN),

  J(new Pose2d(4.8763849011636, 5.2824599169064, Rotation2d.fromDegrees(240)), Apriltag.TWENTY, Apriltag.ELEVEN),

  K(new Pose2d(3.8792387399393, 5.1536819169064, Rotation2d.fromDegrees(300)), Apriltag.NINETEEN, Apriltag.SIX),

  L(new Pose2d(3.5946480611636, 4.9893734119064, Rotation2d.fromDegrees(300)), Apriltag.NINETEEN, Apriltag.SIX);
    // spotless:on

  private final Pose2d bluePosition;
  private final Pose2d redPosition;
  private final Apriltag blueTag;
  private final Apriltag redTag;

  AlignmentPosition(Pose2d position, Apriltag blueTag, Apriltag redTag) {
    this.bluePosition = position;
    Rotation2d redRotation = Rotation2d.fromRadians(Math.PI + position.getRotation().getRadians());
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
    double closestDistance =
        values()[0].getPosition().getTranslation().getDistance(currentPosition);
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
