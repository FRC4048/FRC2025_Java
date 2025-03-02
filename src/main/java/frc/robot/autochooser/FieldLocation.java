package frc.robot.autochooser;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.RobotContainer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum FieldLocation {
  ZERO(0, 0, 0, "Zero", "Zero"),
  INVALID(-1, -1, -1, "INVALID", "INVALID"),
  LEFT(7.150, 6.000, 180, "NON Processor Side", "NON Processor Side"),
  MIDDLE(7.128, 4.173, 180, "Middle", "Middle"),
  RIGHT(7.150, 2.000, 180, "Processor Side", "Processor Side");

  private static final double RED_X_POS = 2.3876; // meters
  private static final double HEIGHT_OF_FIELD = 8.05;
  private final double yPose;
  private final double xPose;
  private final double angle;
  private final String blueName;
  private final String redName;
  private static final HashMap<String, FieldLocation> nameMap =
      new HashMap<>(
          Arrays.stream(FieldLocation.values())
              .collect(Collectors.toMap(FieldLocation::getShuffleboardName, Function.identity())));

  FieldLocation(double xPos, double yPose, double angle, String blueName, String redName) {
    this.xPose = xPos;
    this.yPose = yPose;
    this.angle = angle;
    this.blueName = blueName;
    this.redName = redName;
  }

  public static FieldLocation fromName(String string) {
    return nameMap.get(string);
  }

  public Pose2d getLocation() {
    double x =
        RobotContainer.isRedAlliance() ? xPose + RED_X_POS + Units.inchesToMeters(36) : xPose;
    double y = RobotContainer.isRedAlliance() ? yPose - 2 * (yPose - (HEIGHT_OF_FIELD / 2)) : yPose;
    double radian =
        RobotContainer.isRedAlliance() ? Math.toRadians(180 - angle) : Math.toRadians(angle);
    return new Pose2d(x, y, Rotation2d.fromRadians(radian));
  }

  public String getShuffleboardName() {
    return RobotContainer.isRedAlliance() ? redName : blueName;
  }
}
