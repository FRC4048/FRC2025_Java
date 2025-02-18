package frc.robot.subsystems.LightStrip;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.utils.BlinkinPattern;
import frc.robot.constants.Constants;

public class RealLightStripIO implements LightStripIO {

  private final Spark colorSensorPort;

  public RealLightStripIO() {
    colorSensorPort = new Spark(Constants.LIGHTSTRIP_PORT);
  }

  @Override
  public void setPattern(BlinkinPattern pattern) {
    colorSensorPort.set(pattern.getPwm());
  }

  @Override
  public void updateInputs(LightStripInputs inputs) {
    inputs.pattern = BlinkinPattern.of(colorSensorPort.get());
  }
}
