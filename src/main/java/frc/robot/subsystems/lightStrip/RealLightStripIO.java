package frc.robot.subsystems.lightStrip;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.constants.Constants;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.inputs.LightStripInputs;
import frc.robot.utils.logging.subsystem.providers.InputProvider;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

public class RealLightStripIO implements LightStripIO {

  private final Spark colorSensorPort;
  private final SparkMaxInputProvider inputProvider;

  public RealLightStripIO() {
    colorSensorPort = new Spark(Constants.LIGHTSTRIP_PORT);
    colorSensorPort.set(BlinkinPattern.HOT_PINK.getPwm());
    this.inputProvider = new SparkMaxInputProvider(colorSensorPort);
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
