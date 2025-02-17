package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.simulation.ArmSimulator;

public class SimHihiExtenderIO extends RealHihiExtenderIO {
  private final ArmSimulator hihiExtenderSimulator;

  public SimHihiExtenderIO() {
    super();
    hihiExtenderSimulator =
        new ArmSimulator(
            "HiHi Extender",
            extenderMotor.getNeoMotor(),
            Constants.HIHI_GEARING,
            Constants.HIHI_INERTIA,
            Constants.HIHI_LENGTH,
            Constants.HIHI_MIN_ANGLE,
            Constants.HIHI_MAX_ANGLE,
            Constants.HI_HI_SIMULATE_GRAVITY);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiExtenderSimulator.simulationPeriodic();
    }
  }
}
