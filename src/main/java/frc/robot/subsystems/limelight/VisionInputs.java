package frc.robot.subsystems.limelight;

import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class VisionInputs extends FolderLoggableInputs {
  // 1 if valid target exists. 0 if no valid targets exist
  public int tv = 0;
  // Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees / LL2: -29.8 to 29.8
  // degrees)
  public double tx = 0;
  // Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees / LL2: -24.85 to
  // 24.85 degrees)
  public double ty = 0;
  // Horizontal Offset From Principal Pixel To Target (degrees)
  public double txnc = 0;
  // Vertical Offset From Principal Pixel To Target (degrees)
  public double tync = 0;
  // Target Area (0% of image to 100% of image)
  public double ta = 0;
  // The pipeline's latency contribution (ms). Add to "cl" to get total latency.
  public double tl = 0;
  // Capture pipeline latency (ms). Time between the end of the exposure of the middle row of the
  // sensor to the beginning of the tracking pipeline.
  public double cl = 0;
  // Array containing several values for matched-timestamp statistics: [targetValid, targetCount,
  // targetLatency, captureLatency, tx, ty, txnc, tync, ta, tid, targetClassIndexDetector ,
  // targetClassIndexClassifier, targetLongSidePixels, targetShortSidePixels,
  // targetHorizontalExtentPixels, targetVerticalExtentPixels, targetSkewDegrees]
  public double t2d = 0;
  // True active pipeline index of the camera (0 .. 9)
  public int getpipe = 0;
  // True active pipeline index of the camera (0 .. 9)
  public String getpipetype = "";
  // True active pipeline index of the camera (0 .. 9)
  public String tclass = "";
  // heartbeat value. Increases once per frame, resets at 2 billion
  public double hb = 0;
  // HW metrics [fps, cpu temp, ram usage, temp]
  public double[] hw = new double[0];
  // Name of classifier pipeline's computed class
  public String tcclass = "";
  // Name of classifier pipeline's computed class
  public String tdclass = "";
  // green Led Mode, 0 = use the LED Mode set in the current pipeline, 1 = force off, 2 = force
  // blink, 3 = 	force on
  public int ledMode = -1;

  public VisionInputs(String folder) {
    super(folder);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("tv", tv);
    table.put("tx", tx);
    table.put("ty", ty);
    table.put("txnc", txnc);
    table.put("tync", tync);
    table.put("ta", ta);
    table.put("tl", tl);
    table.put("cl", cl);
    table.put("t2d", t2d);
    table.put("getpipe", getpipe);
    table.put("getpipetype", getpipetype);
    table.put("tclass", tclass);
    table.put("hb", hb);
    table.put("hw", hw);
    table.put("tcclass", tcclass);
    table.put("tdclass", tdclass);
    table.put("ledMode", ledMode);
  }

  @Override
  public void fromLog(LogTable table) {
    tv = table.get("tv", tv);
    tx = table.get("tx", tx);
    ty = table.get("ty", ty);
    txnc = table.get("txnc", txnc);
    tync = table.get("tync", tync);
    ta = table.get("ta", ta);
    tl = table.get("tl", tl);
    cl = table.get("cl", cl);
    t2d = table.get("t2d", t2d);
    getpipe = table.get("getpipe", getpipe);
    getpipetype = table.get("getpipetype", getpipetype);
    tclass = table.get("tclass", tclass);
    hb = table.get("hb", hb);
    hw = table.get("hw", hw);
    tcclass = table.get("tcclass", tcclass);
    tdclass = table.get("tdclass", tdclass);
    ledMode = table.get("ledMode", ledMode);
  }
}
