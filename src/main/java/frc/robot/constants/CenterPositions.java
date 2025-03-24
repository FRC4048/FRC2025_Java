package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

public enum CenterPositions {
  CENTER_AB(new Translation2d(3.8337244850000, 4.0259000000000)),
  CENTER_CD(new Translation2d(4.1615306625000, 3.4581230455351)),
  CENTER_EF(new Translation2d(4.8171430175000, 3.4581230455351)),
  CENTER_GH(new Translation2d(5.1449491950000, 4.0259000000000)),
  CENTER_IJ(new Translation2d(4.8171430175000, 4.5936769544649)),
  CENTER_KL(new Translation2d(4.1615306625000, 4.5936769544649));

  private final Translation2d position;

  CenterPositions(Translation2d position) {
    this.position = position;
  }

  public Translation2d getPosition() {
    return position;
  }

  public static int getClosest(Pose2d robotPose) {
    double closeDistance = 100;
    int n = 0;
    for (int i = 0; i < values().length; i++) {
      double distance = robotPose.getTranslation().getDistance(values()[i].getPosition());
      if (distance < closeDistance) {
        n = i;
        closeDistance = distance;
      }
    }
    return n;
  }
}
