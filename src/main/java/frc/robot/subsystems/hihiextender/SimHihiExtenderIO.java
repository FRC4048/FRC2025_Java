package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class SimHihiExtenderIO extends RealHihiExtenderIO {
  private final HihiExtenderSimulator hihiExtenderSimulator;

  public SimHihiExtenderIO() {
    super();
    hihiExtenderSimulator = new HihiExtenderSimulator(extenderMotor.getNeoMotor());
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiExtenderSimulator.simulationPeriodic();
    }
  }
}
