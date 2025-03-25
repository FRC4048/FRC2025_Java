package frc.robot.subsystems.limelight;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagLimelight;
import java.util.Map;

public class RealVisionIO implements VisionIO {
  private final NetworkTableEntry tv;
  private final NetworkTableEntry tx;
  private final NetworkTableEntry ty;
  private final NetworkTableEntry txnc;
  private final NetworkTableEntry tync;
  private final NetworkTableEntry ta;
  private final NetworkTableEntry tl;
  private final NetworkTableEntry cl;
  private final NetworkTableEntry t2d;
  private final NetworkTableEntry getpipe;
  private final NetworkTableEntry getpipetype;
  private final NetworkTableEntry tclass;
  private final NetworkTableEntry hb;
  private final NetworkTableEntry hw;
  private final NetworkTableEntry tcclass;
  private final NetworkTableEntry tdclass;
  private final NetworkTableEntry ledMode;

  public RealVisionIO() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    tv = table.getEntry("tv");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    txnc = table.getEntry("txnc");
    tync = table.getEntry("tync");
    ta = table.getEntry("ta");
    tl = table.getEntry("tl");
    cl = table.getEntry("cl");
    t2d = table.getEntry("t2d");
    getpipe = table.getEntry("getpipe");
    getpipetype = table.getEntry("getpipetype");
    tclass = table.getEntry("tclass");
    hb = table.getEntry("hb");
    hw = table.getEntry("hw");
    tcclass = table.getEntry("tcclass");
    tdclass = table.getEntry("tdclass");
    ledMode = table.getEntry("ledMode");
    Robot.getDiagnostics().addDiagnosable(new DiagLimelight("Vision", "Piece Seen"));

    HttpCamera limelightFeed =
        new HttpCamera(
            "limelight", "http://" + Constants.LIMELIGHT_IP_ADDRESS + ":5800/stream.mjpg");
    ShuffleboardTab dashboardTab = Shuffleboard.getTab("Driver");
    dashboardTab
        .add("Limelight feed", limelightFeed)
        .withSize(6, 4)
        .withPosition(2, 0)
        .withProperties(Map.of("Show Crosshair", false, "Show Controls", false));
  }

  @Override
  public void updateInputs(VisionInputs inputs) {
    inputs.tv = (int) tv.getInteger(0);
    inputs.tx = tx.getDouble(-1);
    inputs.ty = ty.getDouble(-1);
    inputs.txnc = txnc.getDouble(-1);
    inputs.tync = tync.getDouble(-1);
    inputs.ta = ta.getDouble(-1);
    inputs.tl = tl.getDouble(-1);
    inputs.cl = cl.getDouble(-1);
    inputs.t2d = t2d.getDouble(-1);
    inputs.getpipe = (int) getpipe.getInteger(-1);
    inputs.getpipetype = getpipetype.getString("");
    inputs.tclass = tclass.getString("");
    inputs.hb = hb.getDouble(0);
    inputs.hw = hw.getDoubleArray(new double[0]);
    inputs.tcclass = tcclass.getString("");
    inputs.tdclass = tdclass.getString("");
    inputs.ledMode = (int) ledMode.getInteger(-1);
  }
}
