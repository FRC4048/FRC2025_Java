package frc.robot.subsystems.swervev3.bags;

import edu.wpi.first.math.geometry.Pose2d;

/**
 * measurement estimated robot position calculated from apriltag tag what tag produced the position
 * measurement timeOfMeasurement difference between the time the measurement was received by the
 * robot program and when it was sent over the network
 */
public record VisionMeasurement(Pose2d measurement, double timeOfMeasurement) {
}
