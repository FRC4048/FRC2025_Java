package frc.robot.subsystems.LightStrip;

import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class LightStripInputs extends FolderLoggableInputs {

  public LightStripInputs() {
    super("LightStrip");
  }

  public BlinkinPattern pattern = BlinkinPattern.BLACK;

  @Override
  public void toLog(LogTable table) {
    table.put("patternPWM", pattern.getPwm());
  }

  @Override
  public void fromLog(LogTable table) {
    pattern = BlinkinPattern.of(table.get("patternPWM", pattern.getPwm()));
  }
}
