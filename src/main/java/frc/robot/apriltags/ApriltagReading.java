package frc.robot.apriltags;

public record ApriltagReading(
    double posX,
    double posY,
    double poseYaw,
    double distanceToTag,
    int tag,
    double latency,
    double measurementTime) {}
