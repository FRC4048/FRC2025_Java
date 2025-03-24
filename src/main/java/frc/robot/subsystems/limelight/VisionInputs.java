package frc.robot.subsystems.limelight;

import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class VisionInputs extends FolderLoggableInputs {
  public double tv = 0;
  public double tx = 0;
  public double ty = 0;
  public BranchPositions[] coralSeen;
  public AlgaePositions[] algaeSeen;

  public VisionInputs(String folder) {
    super(folder);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("tv", tv);
    table.put("tx", tx);
    table.put("ty", ty);
    table.put("coralSeen", coralSeen);
    table.put("algaeSeen", algaeSeen);
  }

  @Override
  public void fromLog(LogTable table) {
    tv = table.get("tv", tv);
    tx = table.get("tx", tx);
    ty = table.get("ty", ty);
    coralSeen = table.get("coralSeen", coralSeen);
    algaeSeen = table.get("algaeSeen", algaeSeen);
  }
}
