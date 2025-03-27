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

  A(new Pose2d(3.2076000000000, 4.0614305050000, Rotation2d.fromDegrees(0)), Apriltag.EIGHTEEN, Apriltag.SEVEN, new Pose2d(2.8076000000000, 4.0614305050000, Rotation2d.fromDegrees(0))),

  B(new Pose2d(3.2076000000000, 3.7328134950000, Rotation2d.fromRadians(0)), Apriltag.EIGHTEEN, Apriltag.SEVEN, new Pose2d(2.8076000000000, 3.7328134950000, Rotation2d.fromRadians(0))),

  C(new Pose2d(3.8176981000607, 2.9336485880936, Rotation2d.fromDegrees(60)), Apriltag.SEVENTEEN, Apriltag.EIGHT, new Pose2d(3.6176981000607, 2.5872384265798, Rotation2d.fromDegrees(60))),

  D(new Pose2d(4.1022887788364, 2.7693400830936, Rotation2d.fromDegrees(60)), Apriltag.SEVENTEEN, Apriltag.EIGHT, new Pose2d(3.9022887788364, 2.4229299215798, Rotation2d.fromDegrees(60))),

  E(new Pose2d(5.0994349400607, 2.8981180830936, Rotation2d.fromDegrees(120)), Apriltag.TWENTY_TWO, Apriltag.NINE, new Pose2d(5.2994349400607, 2.5517079215798  , Rotation2d.fromDegrees(120))),

  F(new Pose2d(5.3840256188364, 3.0624265880936, Rotation2d.fromDegrees(120)), Apriltag.TWENTY_TWO, Apriltag.NINE, new Pose2d(5.5840256188364, 2.7160164265798, Rotation2d.fromDegrees(120))),

  G(new Pose2d(5.7710736800000, 3.9903694950000, Rotation2d.fromDegrees(180)), Apriltag.TWENTY_ONE, Apriltag.TEN, new Pose2d(6.1710736800000, 3.9903694950000, Rotation2d.fromDegrees(180))),

  H(new Pose2d(5.7710736800000, 4.3189865050000, Rotation2d.fromDegrees(180)), Apriltag.TWENTY_ONE, Apriltag.TEN, new Pose2d(6.1710736800000, 4.3189865050000, Rotation2d.fromDegrees(180))),

  I(new Pose2d(5.1609755799393, 5.1181514119064, Rotation2d.fromDegrees(240)), Apriltag.TWENTY, Apriltag.ELEVEN, new Pose2d(5.3609755799393, 5.4645615734202, Rotation2d.fromDegrees(240))),

  J(new Pose2d(4.8763849011636, 5.2824599169064, Rotation2d.fromDegrees(240)), Apriltag.TWENTY, Apriltag.ELEVEN, new Pose2d(5.0763849011636, 5.6288700784202, Rotation2d.fromDegrees(240))),

  K(new Pose2d(3.8792387399393, 5.1536819169064, Rotation2d.fromDegrees(300)), Apriltag.NINETEEN, Apriltag.SIX, new Pose2d(3.6792387399393, 5.5000920784202, Rotation2d.fromDegrees(300))),

  L(new Pose2d(3.5946480611636, 4.9893734119064, Rotation2d.fromDegrees(300)), Apriltag.NINETEEN, Apriltag.SIX, new Pose2d(3.3946480611636, 5.3357835734202, Rotation2d.fromDegrees(300)));
    // spotless:on

  private final Pose2d bluePosition;
  private final Pose2d redPosition;
  private final Apriltag blueTag;
  private final Apriltag redTag;
  private final Pose2d bluePathPlannerPosition;
  private final Pose2d redPathPlannerPosition;

  AlignmentPosition(
      Pose2d position, Apriltag blueTag, Apriltag redTag, Pose2d pathPlannerPosition) {
    this.bluePosition = position;
    this.bluePathPlannerPosition = pathPlannerPosition;
    Rotation2d redRotation = Rotation2d.fromRadians(Math.PI + position.getRotation().getRadians());
    double redX = FieldLocation.LENGTH_OF_FIELD - bluePosition.getX();
    double redY = FieldLocation.HEIGHT_OF_FIELD - bluePosition.getY();
    double redPathPlannerX = FieldLocation.LENGTH_OF_FIELD - pathPlannerPosition.getX();
    double redPathPlannerY = FieldLocation.HEIGHT_OF_FIELD - pathPlannerPosition.getY();
    this.redPathPlannerPosition = new Pose2d(redPathPlannerX, redPathPlannerY, redRotation);
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

  public Pose2d getBluePathPlannerPosition() {
    return bluePathPlannerPosition;
  }

  public Pose2d getRedPathPlannerPosition() {
    return redPathPlannerPosition;
  }

  public Pose2d getPosition() {
    Optional<DriverStation.Alliance> allianceColor = Robot.getAllianceColor();
    return allianceColor
        .map(
            alliance ->
                alliance == DriverStation.Alliance.Blue ? getBluePosition() : getRedPosition())
        .orElse(new Pose2d());
  }

  public Pose2d getPathPlannerPosition() {
    Optional<DriverStation.Alliance> allianceColor = Robot.getAllianceColor();
    return allianceColor
        .map(
            alliance ->
                alliance == DriverStation.Alliance.Blue
                    ? getBluePathPlannerPosition()
                    : getRedPathPlannerPosition())
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
