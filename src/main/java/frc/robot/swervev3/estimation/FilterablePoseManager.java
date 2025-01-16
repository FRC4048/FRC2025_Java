package frc.robot.swervev3.estimation;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.interpolation.TimeInterpolatableBuffer;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N3;
import org.littletonrobotics.junction.Logger;
import frc.robot.swervev3.bags.OdometryMeasurement;
import frc.robot.swervev3.bags.VisionMeasurement;
import frc.robot.swervev3.vision.FilterResult;
import frc.robot.swervev3.vision.PoseDeviation;
import frc.robot.swervev3.vision.VisionFilter;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A subclass of PoseManager that filters vision measurements before they are fed into the kalman filter.
 */
public class FilterablePoseManager extends PoseManager {
    private final VisionFilter filter;

    public FilterablePoseManager(PoseDeviation PoseDeviation, SwerveDriveKinematics kinematics, OdometryMeasurement initialOdom, TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer, VisionFilter filter) {
        super(PoseDeviation, kinematics, initialOdom, estimatedPoseBuffer);
        this.filter = filter;
    }

    public FilterablePoseManager(Vector<N3> wheelStd, Vector<N3> visionStd, SwerveDriveKinematics kinematics, OdometryMeasurement initialOdom, TimeInterpolatableBuffer<Pose2d> estimatedPoseBuffer, VisionFilter filter) {
        this(new PoseDeviation(wheelStd, visionStd), kinematics, initialOdom, estimatedPoseBuffer, filter);
    }

    @Override
    protected void processQueue() {
        LinkedHashMap<VisionMeasurement, FilterResult> filter1 = filter.filter(visionMeasurementQueue);
        visionMeasurementQueue.clear();
        AtomicInteger numRejected = new AtomicInteger();
        filter1.forEach((v, r) -> {
            switch (r){
                case ACCEPTED -> addVisionMeasurement(v);
                case NOT_PROCESSED -> visionMeasurementQueue.add(v);
                case REJECTED -> numRejected.getAndIncrement();
            }
        });
        Logger.recordOutput("rejectedMeasurementsCount", numRejected.get());
    }
}