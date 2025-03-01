package frc.robot.subsystems.lightStrip;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.LoggableSystem;

public class LightStrip extends SubsystemBase {
  private final LoggableSystem<LightStripIO, LightStripInputs> system;

  public LightStrip(LightStripIO io) {
    this.system = new LoggableSystem<>(io, new LightStripInputs());
  }

  public void setPattern(BlinkinPattern pattern) {
    system.getIO().setPattern(pattern);
  }

  public BlinkinPattern getPattern() {
    return system.getInputs().pattern;
  }
}
