package frc.robot.subsystems.lightStrip;

import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.LoggableIO;

public interface LightStripIO extends LoggableIO<LightStripInputs> {
  void setPattern(BlinkinPattern pattern);
}
