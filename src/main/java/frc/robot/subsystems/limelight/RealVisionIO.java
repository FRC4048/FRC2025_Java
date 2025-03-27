package frc.robot.subsystems.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Robot;
import frc.robot.utils.LimelightHelpers;
import frc.robot.utils.diag.DiagLimelight;

public class RealVisionIO implements VisionIO {
  private LimelightHelpers.LimelightResults results;
  private final NetworkTableEntry ledModeEntry;

  public RealVisionIO() {
    Robot.getDiagnostics().addDiagnosable(new DiagLimelight("Vision", "Piece Seen"));

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    ledModeEntry = table.getEntry("ledMode");
  }

  @Override
  public void updateInputs(VisionInputs inputs) {
    // this might take a while so we might now want to update this every tick and do it in another
    // thread.
    results = LimelightHelpers.getLatestResults("limelight");
    LimelightHelpers.LimelightTarget_Detector[] targetsDetector = results.targets_Detector;
    int detectionLength = targetsDetector.length;
    inputs.className = new String[detectionLength];
    inputs.classID = new double[detectionLength];
    inputs.confidence = new double[detectionLength];
    inputs.ta = new double[detectionLength];
    inputs.tx = new double[detectionLength];
    inputs.ty = new double[detectionLength];
    inputs.tx_pixels = new double[detectionLength];
    inputs.ty_pixels = new double[detectionLength];
    for (int i = 0; i < detectionLength; i++) {
      inputs.className[i] = targetsDetector[i].className;
      inputs.classID[i] = targetsDetector[i].classID;
      inputs.confidence[i] = targetsDetector[i].confidence;
      inputs.ta[i] = targetsDetector[i].ta;
      inputs.tx[i] = targetsDetector[i].tx;
      inputs.ty[i] = targetsDetector[i].ty;
      inputs.tx_pixels[i] = targetsDetector[i].tx_pixels;
      inputs.ty_pixels[i] = targetsDetector[i].ty_pixels;
    }
    inputs.pipelineID = results.pipelineID;
    inputs.latency_pipeline = results.latency_pipeline;
    inputs.latency_capture = results.latency_capture;
    inputs.latency_jsonParse = results.latency_jsonParse;
    inputs.timestamp_LIMELIGHT_publish = results.timestamp_LIMELIGHT_publish;
    inputs.timestamp_RIOFPGA_capture = results.timestamp_RIOFPGA_capture;
    inputs.valid = results.valid;
    inputs.ledMode = (int) ledModeEntry.getInteger(-1);
  }
}
