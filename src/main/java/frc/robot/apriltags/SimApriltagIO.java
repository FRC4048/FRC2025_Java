package frc.robot.apriltags;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;
import frc.robot.utils.Apriltag;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.inputs.VisionInputs;
import java.util.Arrays;
import org.littletonrobotics.junction.Logger;

public class SimApriltagIO implements LoggableIO<ApriltagInputs> {
  private final LoggableSystem<VisionIOPhotonVisionSim, VisionInputs> visionSystem;

  public SimApriltagIO(VisionIOPhotonVisionSim vision) {
    VisionInputs inputs = new VisionInputs("vision");
    visionSystem = new LoggableSystem<>(vision, inputs);
  }

  @Override
  public void updateInputs(ApriltagInputs inputs) {
    inputs.timestamp =
        Arrays.stream(visionSystem.getInputs().poseObservations)
            .mapToDouble(VisionIO.PoseObservation::timestamp)
            .toArray();
    inputs.serverTime =
        Arrays.stream(visionSystem.getInputs().poseObservations)
            .mapToDouble(VisionIO.PoseObservation::timestamp)
            .toArray();
    inputs.posX =
        Arrays.stream(visionSystem.getInputs().poseObservations)
            .mapToDouble(p -> p.pose().getX())
            .toArray();
    inputs.posY =
        Arrays.stream(visionSystem.getInputs().poseObservations)
            .mapToDouble(p -> p.pose().getY())
            .toArray();
    inputs.poseYaw =
        Arrays.stream(visionSystem.getInputs().poseObservations)
            .mapToDouble(p -> p.pose().getRotation().getMeasureZ().in(Degrees))
            .toArray();
    inputs.distanceToTag =
        Arrays.stream(visionSystem.getInputs().poseObservations)
            .mapToDouble(VisionIO.PoseObservation::averageTagDistance)
            .toArray();
    inputs.apriltagNumber = visionSystem.getInputs().tagIds;
    Apriltag[] apriltag =
        Arrays.stream(visionSystem.getInputs().tagIds)
            .mapToObj(Apriltag::of)
            .toArray(Apriltag[]::new);
    Translation3d[] apriltagPoseArray =
        Arrays.stream(apriltag).map(Apriltag::getTranslation).toArray(Translation3d[]::new);
    Pose2d[] visionPoseArray =
        Arrays.stream(visionSystem.getInputs().poseObservations)
            .map(p -> p.pose().toPose2d())
            .toArray(Pose2d[]::new);
    Logger.recordOutput("Apriltag/TagPoses", apriltagPoseArray);
    Logger.recordOutput("Apriltag/VisionPoses", visionPoseArray);
  }

  public void accept(
      Pose2d visionRobotPoseMeters,
      double timestampSeconds,
      Matrix<N3, N1> visionMeasurementStdDevs) {}
}
