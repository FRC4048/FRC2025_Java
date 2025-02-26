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
  LEFT(7.306, 7.057, 180, "Left", "Left"),
  MIDDLE(7.128, 4.173, 180, "Middle", "Middle"),
  RIGHT(7.225, 1.048, 180, "Right", "Right");

  private static final double RED_X_POS = 2.3876; // meters
  private final double yPos;
  private final double xPose;
  private final double angle;
  private final String blueName;
  private final String redName;
  private static final HashMap<String, FieldLocation> nameMap =
      new HashMap<>(
          Arrays.stream(FieldLocation.values())
              .collect(Collectors.toMap(FieldLocation::getShuffleboardName, Function.identity())));

  FieldLocation(double xPos, double yPos, double angle, String blueName, String redName) {
    this.xPose = xPos;
    this.yPos = yPos;
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
    double radian =
        RobotContainer.isRedAlliance() ? Math.toRadians(180 - angle) : Math.toRadians(angle);
    return new Pose2d(x, yPos, Rotation2d.fromRadians(radian));
  }

  public String getShuffleboardName() {
    return RobotContainer.isRedAlliance() ? redName : blueName;
  }
}
