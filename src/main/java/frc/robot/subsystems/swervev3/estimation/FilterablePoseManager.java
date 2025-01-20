package frc.robot.subsystems.swervev3.estimation;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N3;
import frc.robot.subsystems.swervev3.bags.OdometryMeasurement;
import frc.robot.subsystems.swervev3.bags.VisionMeasurement;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.littletonrobotics.junction.Logger;

/**
 * A subclass of PoseManager that filters vision measurements before they are fed into the kalman
 * filter.
 */
public class FilterablePoseManager extends PoseManager {

  public FilterablePoseManager(
      SwerveDriveKinematics kinematics,
      OdometryMeasurement initialOdom,
      TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer) {
    super(kinematics, initialOdom, estimatedPoseBuffer);
  }

  public FilterablePoseManager(
      Vector<N3> wheelStd,
      Vector<N3> visionStd,
      SwerveDriveKinematics kinematics,
      OdometryMeasurement initialOdom,
      TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer) {
    this(
        kinematics,
        initialOdom,
        estimatedPoseBuffer);
  }

  @Override
  protected void processQueue() {
    visionMeasurementQueue.clear();
    AtomicInteger numRejected = new AtomicInteger();
    Logger.recordOutput("rejectedMeasurementsCount", numRejected.get());
  }
}
