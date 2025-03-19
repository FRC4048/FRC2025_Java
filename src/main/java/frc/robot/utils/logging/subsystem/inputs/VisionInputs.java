package frc.robot.utils.logging.subsystem.inputs;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.vision.VisionIO.PoseObservation;
import frc.robot.subsystems.vision.VisionIO.TargetObservation;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class VisionInputs extends FolderLoggableInputs {
  public boolean connected = false;
  public TargetObservation latestTargetObservation =
      new TargetObservation(new Rotation2d(), new Rotation2d());
  public PoseObservation[] poseObservations = new PoseObservation[0];
  public int[] tagIds = new int[0];

  public VisionInputs(String key) {
    super(key);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("connected", connected);
    table.put("latestTargetObservation", latestTargetObservation);
    table.put("tagIds.length", tagIds.length);
    table.put("poseObservations", poseObservations);
  }

  @Override
  public void fromLog(LogTable table) {
    connected = table.get("connected", connected);
    latestTargetObservation = table.get("latestTargetObservation", latestTargetObservation);
    tagIds = table.get("tagIds", tagIds);
    poseObservations = table.get("poseObservations", poseObservations);
  }
}
