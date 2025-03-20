package frc.robot.apriltags;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.utils.Apriltag;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.VisionInputs;
import java.util.Arrays;
import org.littletonrobotics.junction.Logger;

public class SimApriltagIO implements LoggableIO<ApriltagInputs> {
  private final VisionIO[] io;
  private final VisionInputs[] visionInputs;
  private VisionIO.PoseObservation[] poseObservations;

  public SimApriltagIO(VisionIO... io) {
    this.io = io;
    // Initialize inputs
    this.visionInputs = new VisionInputs[io.length];
    for (int i = 0; i < visionInputs.length; i++) {
      visionInputs[i] = new VisionInputs("Vision");
    }
  }

  @Override
  public void updateInputs(ApriltagInputs inputs) {
    for (int i = 0; i < io.length; i++) {
      io[i].updateInputs(visionInputs[i]);
      Logger.processInputs("Vision/Camera" + Integer.toString(i), visionInputs[i]);
    }
    for (int cameraIndex = 0; cameraIndex < io.length; cameraIndex++) {
      poseObservations = visionInputs[cameraIndex].poseObservations;
    }
    inputs.timestamp =
        Arrays.stream(poseObservations).mapToDouble(VisionIO.PoseObservation::timestamp).toArray();
    inputs.serverTime =
        Arrays.stream(poseObservations).mapToDouble(VisionIO.PoseObservation::timestamp).toArray();
    inputs.posX = Arrays.stream(poseObservations).mapToDouble(p -> p.pose().getX()).toArray();
    inputs.posY = Arrays.stream(poseObservations).mapToDouble(p -> p.pose().getY()).toArray();
    inputs.poseYaw =
        Arrays.stream(poseObservations)
            .mapToDouble(p -> p.pose().getRotation().getMeasureZ().in(Degrees))
            .toArray();
    inputs.distanceToTag =
        Arrays.stream(poseObservations)
            .mapToDouble(VisionIO.PoseObservation::averageTagDistance)
            .toArray();
    inputs.apriltagNumber =
        Arrays.stream(poseObservations).mapToInt(VisionIO.PoseObservation::tagId).toArray();
    Apriltag[] apriltag =
        Arrays.stream(poseObservations)
            .mapToInt(VisionIO.PoseObservation::tagId)
            .mapToObj(Apriltag::of)
            .toArray(Apriltag[]::new);
    Translation3d[] apriltagPoseArray =
        Arrays.stream(apriltag).map(Apriltag::getTranslation).toArray(Translation3d[]::new);
    Pose2d[] visionPoseArray =
        Arrays.stream(poseObservations).map(p -> p.pose().toPose2d()).toArray(Pose2d[]::new);
    Logger.recordOutput("Apriltag/TagPoses", apriltagPoseArray);
    Logger.recordOutput("Apriltag/VisionPoses", visionPoseArray);
  }
}
