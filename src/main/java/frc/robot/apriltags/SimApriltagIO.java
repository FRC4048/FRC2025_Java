package frc.robot.apriltags;

import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;

public class SimApriltagIO implements ApriltagIO {
  private final VisionIOPhotonVisionSim vision;

  public SimApriltagIO(VisionIOPhotonVisionSim vision) {
    this.vision = vision;
  }

  @Override
  public void updateInputs(ApriltagInputs inputs) {}
}
