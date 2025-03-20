package frc.robot.apriltags;

import frc.robot.subsystems.vision.Vision.VisionConsumer;
import frc.robot.subsystems.vision.VisionIO.PoseObservation;

public class ApriltagConsumer implements VisionConsumer {
  public PoseObservation[] poseObservations = new PoseObservation[0];

  public void accept(PoseObservation[] poseObservations) {
    this.poseObservations = poseObservations;
  }
}
