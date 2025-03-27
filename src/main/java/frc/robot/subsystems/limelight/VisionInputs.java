package frc.robot.subsystems.limelight;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class VisionInputs extends FolderLoggableInputs {
  public double pipelineID;
  public double latency_pipeline;
  public double latency_capture;
  public double latency_jsonParse;
  public double timestamp_LIMELIGHT_publish;
  public double timestamp_RIOFPGA_capture;
  public boolean valid;
  public int ledMode = -1;

  // from LimelightHelpers.LimelightTarget_Detector[]
  public String[] className = new String[0];
  public double[] classID = new double[0];
  public double[] confidence = new double[0];
  public double[] ta = new double[0];
  public double[] tx = new double[0];
  public double[] ty = new double[0];
  public double[] tx_pixels = new double[0];
  public double[] ty_pixels = new double[0];

  public VisionInputs(String folder) {
    super(folder);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("pipelineID", pipelineID);
    table.put("latency_pipeline", latency_pipeline);
    table.put("latency_capture", latency_capture);
    table.put("latency_jsonParse", latency_jsonParse);
    table.put("timestamp_LIMELIGHT_publish", timestamp_LIMELIGHT_publish);
    table.put("timestamp_RIOFPGA_capture", timestamp_RIOFPGA_capture);
    table.put("valid", valid);
    table.put("classNames", className);
    table.put("classID", classID);
    table.put("confidence", confidence);
    table.put("ta", ta);
    table.put("tx", tx);
    table.put("ty", ty);
    table.put("tx_pixels", tx_pixels);
    table.put("ty_pixels", ty_pixels);
    table.put("ledMode", ledMode);
  }

  @Override
  public void fromLog(LogTable table) {
    pipelineID = table.get("pipelineID", pipelineID);
    latency_pipeline = table.get("latency_pipeline", latency_pipeline);
    latency_capture = table.get("latency_capture", latency_capture);
    latency_jsonParse = table.get("latency_jsonParse", latency_jsonParse);
    timestamp_LIMELIGHT_publish =
        table.get("timestamp_LIMELIGHT_publish", timestamp_LIMELIGHT_publish);
    timestamp_RIOFPGA_capture = table.get("timestamp_RIOFPGA_capture", timestamp_RIOFPGA_capture);
    valid = table.get("valid", valid);
    className = table.get("classNames", className);
    classID = table.get("classID", classID);
    confidence = table.get("confidence", confidence);
    ta = table.get("ta", ta);
    tx = table.get("tx", tx);
    ty = table.get("ty", ty);
    tx_pixels = table.get("tx_pixels", tx_pixels);
    ty_pixels = table.get("ty_pixels", ty_pixels);
    ledMode = table.get("ledMode", ledMode);
  }
}
