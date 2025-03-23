package frc.robot.subsystems.limelight;

import frc.robot.constants.AlgaePositions;
import frc.robot.constants.BranchPositions;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import java.util.ArrayList;
import java.util.List;
import org.littletonrobotics.junction.LogTable;

public class VisionInputs extends FolderLoggableInputs {
  public double tv = 0;
  public double tx = 0;
  public double ty = 0;
  public ArrayList<BranchPositions> coralSeen;
  public ArrayList<AlgaePositions> algaeSeen;

  public VisionInputs(String folder) {
    super(folder);
  }

  @Override
  public void toLog(LogTable table) {
    table.put("tv", tv);
    table.put("tx", tx);
    table.put("ty", ty);
    if (coralSeen != null) {
      table.put("coralSeen", coralSeen.toArray(BranchPositions[]::new));
    }
    if (algaeSeen != null) {
      table.put("algaeSeen", algaeSeen.toArray(AlgaePositions[]::new));
    }
  }

  @Override
  public void fromLog(LogTable table) {
    tv = table.get("tv", tv);
    tx = table.get("tx", tx);
    ty = table.get("ty", ty);
    coralSeen =
        new ArrayList<>(List.of(table.get("coralSeen", coralSeen.toArray(BranchPositions[]::new))));
    algaeSeen =
        new ArrayList<>(List.of(table.get("algaeSeen", algaeSeen.toArray(AlgaePositions[]::new))));
  }
}
