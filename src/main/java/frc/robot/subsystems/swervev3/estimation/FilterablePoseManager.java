package frc.robot.subsystems.swervev3.estimation;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;
import frc.robot.subsystems.swervev3.vision.FilterResult;
import frc.robot.subsystems.swervev3.vision.PoseDeviation;
import frc.robot.subsystems.swervev3.vision.VisionFilter;
import frc.robot.subsystems.swervev3.vision.VisionTruster;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.littletonrobotics.junction.Logger;

/**
 * A subclass of PoseManager that filters vision measurements before they are fed into the kalman
 * filter.
 */
public class FilterablePoseManager extends PoseManager {
  private final VisionFilter filter;
  private final VisionTruster visionTruster;

  public FilterablePoseManager(
      PoseDeviation PoseDeviation,
      SwerveDriveKinematics kinematics,
      OdometryMeasurement initialOdom,
      TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer,
      VisionFilter filter,
      VisionTruster visionTruster) {
    super(PoseDeviation, kinematics, initialOdom, estimatedPoseBuffer);
    this.filter = filter;
    this.visionTruster = visionTruster;
  }

  public FilterablePoseManager(
      Vector<N3> wheelStd,
      Vector<N3> visionStd,
      SwerveDriveKinematics kinematics,
      OdometryMeasurement initialOdom,
      TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer,
      VisionFilter filter,
      VisionTruster visionTruster) {
    this(
        new PoseDeviation(wheelStd, visionStd),
        kinematics,
        initialOdom,
        estimatedPoseBuffer,
        filter,
        visionTruster);
  }

  @Override
  public void processQueue() {
    LinkedHashMap<VisionMeasurement, FilterResult> filteredData =
        filter.filter(visionMeasurementQueue);
    visionMeasurementQueue.clear();
    List<Pose2d> validMeasurements = new ArrayList<>();
    List<Pose2d> invalidMeasurements = new ArrayList<>();
    for (Map.Entry<VisionMeasurement, FilterResult> entry : filteredData.entrySet()) {
      VisionMeasurement v = entry.getKey();
      FilterResult r = entry.getValue();
      switch (r) {
        case ACCEPTED -> {
          setVisionSTD(visionTruster.calculateTrust(v));
          validMeasurements.add(v.measurement());
          addVisionMeasurement(v);
        }
        case NOT_PROCESSED -> visionMeasurementQueue.add(v);
        case REJECTED -> {
          invalidMeasurements.add(v.measurement());
        }
      }
    }
    Logger.recordOutput("Apriltag/acceptedMeasurements", validMeasurements.toArray(Pose2d[]::new));
    Logger.recordOutput(
        "Apriltag/rejectedMeasurements", invalidMeasurements.toArray(Pose2d[]::new));
  }

  public VisionTruster getVisionTruster() {
    return visionTruster;
  }
}
