package frc.robot.utils.logging.subsystem.providers;

import frc.robot.utils.BlinkinPattern;

public interface LightStripProvider extends InputProvider {

  BlinkinPattern getPatternPWM();
}
