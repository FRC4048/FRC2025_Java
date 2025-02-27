package frc.robot.subsystems.lightStrip;

import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.inputs.LightStripInputs;

public class MockLightStripIO implements LightStripIO {
  @Override
  public void setPattern(BlinkinPattern pattern) {}

  @Override
  public void updateInputs(LightStripInputs inputs) {}
}
