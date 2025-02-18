package frc.robot.subsystems.LightStrip;

import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.LoggableIO;

public interface LightStripIO extends LoggableIO<LightStripInputs> {
  void setPattern(BlinkinPattern pattern);
}
