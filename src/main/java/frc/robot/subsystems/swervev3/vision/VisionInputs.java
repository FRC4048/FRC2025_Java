package frc.robot.subsystems.swervev3.vision;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class VisionInputs extends FolderLoggableInputs {
    public double[] timestamp = new double[0];
    public double[] serverTime = new double[0];
    public Pose2d[] position = new Pose2d[0];
    public double[] distanceFromTag = new double[0];
    public FilterResult[] filterResults = new FilterResult[0];
    public VisionInputs(String folder) {
        super(folder);
    }
    public void toLog(LogTable table) {
        table.put("timestamp", timestamp);
        table.put("serverTime", serverTime);
        table.put("distanceFromTag", distanceFromTag);
        table.put("filterResults", filterResults);
    }
    public void fromLog(LogTable table) {
        this.timestamp = table.get("timestamp", timestamp);
        this.serverTime = table.get("serverTime", serverTime);
        this.position = table.get("position", position);
        this.distanceFromTag = table.get("distanceFromTag", distanceFromTag);
        this.filterResults = table.get("filterResults", filterResults);
    }
}
