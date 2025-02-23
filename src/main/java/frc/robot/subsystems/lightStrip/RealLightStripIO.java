package frc.robot.subsystems.lightStrip;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.constants.Constants;
import frc.robot.utils.BlinkinPattern;

public class RealLightStripIO implements LightStripIO {

  private final Spark colorSensorPort;

  public RealLightStripIO() {
    colorSensorPort = new Spark(Constants.LIGHTSTRIP_PORT);
    colorSensorPort.set(BlinkinPattern.BEATS_PER_MINUTE_RAINBOW_PALETTE.getPwm());
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
