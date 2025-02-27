package frc.robot.subsystems.lightStrip;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.constants.Constants;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.inputs.LightStripInputs;
import frc.robot.utils.logging.subsystem.providers.LightStripInputProvider;

public class RealLightStripIO implements LightStripIO {

  private final Spark colorSensorPort;
  private final LightStripInputProvider inputProvider;

  public RealLightStripIO() {
    colorSensorPort = new Spark(Constants.LIGHTSTRIP_PORT);
    colorSensorPort.set(BlinkinPattern.HOT_PINK.getPwm());
    this.inputProvider = new LightStripInputProvider(colorSensorPort);
  }

  @Override
  public void setPattern(BlinkinPattern pattern) {
    colorSensorPort.set(pattern.getPwm());
  }

  @Override
  public void updateInputs(LightStripInputs inputs) {
    inputs.process(inputProvider);
  }
}
