package frc.robot.apriltags;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.utils.Apriltag;
import frc.robot.utils.logging.LoggableIO;
import java.util.Arrays;
import org.littletonrobotics.junction.Logger;

public class SimApriltagIO implements LoggableIO<ApriltagInputs> {
  private final ApriltagConsumer consumer;

  public SimApriltagIO(ApriltagConsumer consumer) {
    this.consumer = consumer;
  }

  @Override
  public void updateInputs(ApriltagInputs inputs) {
    inputs.timestamp =
        Arrays.stream(consumer.poseObservations)
            .mapToDouble(VisionIO.PoseObservation::timestamp)
            .toArray();
    inputs.serverTime =
        Arrays.stream(consumer.poseObservations)
            .mapToDouble(VisionIO.PoseObservation::timestamp)
            .toArray();
    inputs.posX =
        Arrays.stream(consumer.poseObservations).mapToDouble(p -> p.pose().getX()).toArray();
    inputs.posY =
        Arrays.stream(consumer.poseObservations).mapToDouble(p -> p.pose().getY()).toArray();
    inputs.poseYaw =
        Arrays.stream(consumer.poseObservations)
            .mapToDouble(p -> p.pose().getRotation().getMeasureZ().in(Degrees))
            .toArray();
    inputs.distanceToTag =
        Arrays.stream(consumer.poseObservations)
            .mapToDouble(VisionIO.PoseObservation::averageTagDistance)
            .toArray();
    inputs.apriltagNumber =
        Arrays.stream(consumer.poseObservations)
            .mapToInt(VisionIO.PoseObservation::tagId)
            .toArray();
    Apriltag[] apriltag =
        Arrays.stream(consumer.poseObservations)
            .mapToInt(VisionIO.PoseObservation::tagId)
            .mapToObj(Apriltag::of)
            .toArray(Apriltag[]::new);
    Translation3d[] apriltagPoseArray =
        Arrays.stream(apriltag).map(Apriltag::getTranslation).toArray(Translation3d[]::new);
    Pose2d[] visionPoseArray =
        Arrays.stream(consumer.poseObservations)
            .map(p -> p.pose().toPose2d())
            .toArray(Pose2d[]::new);
    Logger.recordOutput("Apriltag/TagPoses", apriltagPoseArray);
    Logger.recordOutput("Apriltag/VisionPoses", visionPoseArray);
  }
}
