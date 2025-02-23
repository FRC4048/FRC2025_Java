package frc.robot.commands.lightStrip;

import frc.robot.subsystems.lightStrip.LightStrip;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.commands.LoggableCommand;

public class SetLedPattern extends LoggableCommand {
  private final LightStrip lightStrip;
  private final BlinkinPattern pattern;

  public SetLedPattern(LightStrip lightStrip, BlinkinPattern pattern) {
    this.lightStrip = lightStrip;
    this.pattern = pattern;
    addRequirements(lightStrip);
  }

  @Override
  public void initialize() {
    lightStrip.setPattern(pattern);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
