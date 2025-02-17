package frc.robot.subsystems.hihiextender;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.simulation.ArmParameters;
import frc.robot.utils.simulation.ArmSimulator;

public class SimHihiExtenderIO extends RealHihiExtenderIO {
  private final ArmSimulator hihiExtenderSimulator;
  private ArmParameters params = new ArmParameters();

  public SimHihiExtenderIO() {
    super();
    params.name = "HiHi Extender";
    params.armGearing = Constants.HIHI_GEARING;
    params.armInertia = Constants.HIHI_INERTIA;
    params.armLength = Constants.HIHI_LENGTH;
    params.armMinAngle = Constants.HIHI_MIN_ANGLE;
    params.armMaxAngle = Constants.HIHI_MAX_ANGLE;
    params.armSimulateGravity = Constants.HI_HI_SIMULATE_GRAVITY;

    hihiExtenderSimulator = new ArmSimulator(extenderMotor.getNeoMotor(), params);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiExtenderSimulator.simulationPeriodic();
    }
  }
}
