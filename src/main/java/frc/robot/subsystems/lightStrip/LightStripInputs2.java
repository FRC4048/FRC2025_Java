package frc.robot.subsystems.lightStrip;

import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.FolderLoggableInputs;
import org.littletonrobotics.junction.LogTable;

public class LightStripInputs2 extends FolderLoggableInputs {

  public LightStripInputs2() {
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
