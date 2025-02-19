package frc.robot.autochooser;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.RobotContainer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum FieldLocation {
  ZERO(0, 0, 0, "Zero", "Zero"),
  INVALID(-1, -1, -1, "INVALID", "INVALID"),
  LEFT(7.110, 7.057, -179.995, "Left", "Left"),
  MIDDLE(7.128, 4.173, -179.995, "Middle", "Middle"),
  RIGHT(7.110, 1.041, -179.995, "Right", "Right");

  private static final double RED_X_POS = 16.5; // meters
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
    return null;
  }

  public Pose2d getLocation() {
    double x = RobotContainer.isRedAlliance() ? RED_X_POS - xPose : xPose;
    double radian =
        RobotContainer.isRedAlliance() ? Math.toRadians(180 - angle) : Math.toRadians(angle);
    return new Pose2d(x, yPos, Rotation2d.fromRadians(radian));
  }

  public String getShuffleboardName() {
    return RobotContainer.isRedAlliance() ? redName : blueName;
  }
}
