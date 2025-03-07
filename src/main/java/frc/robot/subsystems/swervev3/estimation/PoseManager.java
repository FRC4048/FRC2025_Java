package frc.robot.subsystems.swervev3.estimation;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;
import frc.robot.subsystems.swervev3.vision.PoseDeviation;
import java.util.LinkedList;
import java.util.Queue;
import org.littletonrobotics.junction.Logger;

/**
 * Processes swerve odometry. Feeds odometry measurements and vision measurements into a Kalman
 * Filter which outputs a combined robot position
 */
public class PoseManager {
  private final TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer;
  private final SwerveDrivePoseEstimator poseEstimator;
  protected final Queue<VisionMeasurement> visionMeasurementQueue = new LinkedList<>();

  public PoseManager(
      PoseDeviation PoseDeviation,
      SwerveDriveKinematics kinematics,
      OdometryMeasurement initialOdom,
      TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer) {
    this.poseEstimator =
        new SwerveDrivePoseEstimator(
            kinematics,
            Rotation2d.fromDegrees(initialOdom.gyroValueDeg()),
            initialOdom.modulePosition(),
            new Pose2d(),
            PoseDeviation.getWheelStd(),
            PoseDeviation.getVisionStd());
    this.estimatedPoseBuffer = estimatedPoseBuffer;
  }

  public PoseManager(
      Vector<N3> wheelStd,
      Vector<N3> visionStd,
      SwerveDriveKinematics kinematics,
      OdometryMeasurement initialOdom,
      TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer) {
    this(new PoseDeviation(wheelStd, visionStd), kinematics, initialOdom, estimatedPoseBuffer);
  }

  public void addOdomMeasurement(OdometryMeasurement m, long timestamp) {
    Rotation2d gyroVal = Rotation2d.fromDegrees(m.gyroValueDeg());
    Pose2d pose = poseEstimator.update(gyroVal, m.modulePosition());
    estimatedPoseBuffer.addSample(timestamp, pose);
  }

  public void registerVisionMeasurement(VisionMeasurement measurement) {
    visionMeasurementQueue.add(measurement);
    processQueue();
  }

  // override for filtering
  protected void processQueue() {
    VisionMeasurement m = visionMeasurementQueue.poll();
    while (m != null) {
      addVisionMeasurement(m);
      m = visionMeasurementQueue.poll();
    }
  }

  protected void addVisionMeasurement(VisionMeasurement measurement) {
    poseEstimator.addVisionMeasurement(measurement.measurement(), measurement.timeOfMeasurement());
  }

  protected void setVisionSTD(Vector<N3> visionMeasurementStdDevs) {
    Logger.recordOutput(
        "Apriltag/VisionAppliedCovariance",
        new double[] {visionMeasurementStdDevs.get(0), visionMeasurementStdDevs.get(1)});
    poseEstimator.setVisionMeasurementStdDevs(visionMeasurementStdDevs);
  }

  public void resetPose(OdometryMeasurement m, Translation2d initialPose) {
    poseEstimator.resetPosition(
        Rotation2d.fromDegrees(m.gyroValueDeg()),
        m.modulePosition(),
        new Pose2d(initialPose, Rotation2d.fromDegrees(m.gyroValueDeg())));
  }

  public TimeInterpolatableBuffer<Pose2d> getPoseBuffer() {
    return estimatedPoseBuffer;
  }

  public Pose2d getEstimatedPosition() {
    return poseEstimator.getEstimatedPosition();
  }
}
