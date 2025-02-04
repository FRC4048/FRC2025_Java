package frc.robot.subsystems.hihiroller;

import frc.robot.constants.Constants;

public class SimHihiRollerIO extends RealHihiRollerIO {
  private final HihiRollerSimulator hihiRollerSimulator;

  public SimHihiRollerIO() {
    super();
    hihiRollerSimulator = new HihiRollerSimulator((hihiRollerMotor));
  }

  @Override
  public void updateInputs(HihiRollerInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiRollerSimulator.simulationPeriodic();
    }
  }
}
