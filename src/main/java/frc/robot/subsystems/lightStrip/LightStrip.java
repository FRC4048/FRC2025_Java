package frc.robot.subsystems.lightStrip;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.BlinkinPattern;
import frc.robot.utils.logging.subsystem.LoggableSystem;
import frc.robot.utils.logging.subsystem.builders.LightStripInputsBuilder;
import frc.robot.utils.logging.subsystem.builders.MotorInputBuilder;
import frc.robot.utils.logging.subsystem.inputs.LightStripInputs;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class LightStrip extends SubsystemBase {
  
  private final LoggableSystem<LightStripIO, LightStripInputs> system;

  
    public LightStrip(LightStripIO io) {
    LightStripInputs inputs = new LightStripInputsBuilder<>("LightStripSubsystem").addPatternPWM().Build();
    this.system = new LoggableSystem<>(io, inputs);
  }

  public void setPattern(BlinkinPattern pattern) {
    system.getIO().setPattern(pattern);
  }

  public BlinkinPattern getPattern() {
    return system.getInputs().;
  }
}
