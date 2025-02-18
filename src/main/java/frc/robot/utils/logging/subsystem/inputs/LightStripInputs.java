package frc.robot.utils.logging.subsystem.inputs;

import org.littletonrobotics.junction.LogTable;

import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import frc.robot.subsystems.LightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;


public class LightStripInputs extends FolderInputs{
  public BlinkinPattern pattern = BlinkinPattern.BLACK;
  private final LightStripInputsBuilder<?> builder;

  public LightStripInputs(LightStripInputsBuilder<?> builder) {
    super(builder.getFolder());
   
    this.builder = builder;
  }

  @Override
  public void toLog(LogTable table) {
    table.put("patternPWM", pattern.getPwm());
  }

  @Override
  public void fromLog(LogTable table) {
    pattern = BlinkinPattern.of(table.get("patternPWM", pattern.getPwm()));
  }
}
