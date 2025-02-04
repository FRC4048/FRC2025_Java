package frc.robot.subsystems.algaebyebyeroller;

import frc.robot.constants.Constants;

public class SimAlgaeByeByeRollerIO extends RealAlgaeByeByeRollerIO {
  private final AlgeaByeByeRollerSimulator algeaByeByeRollerSimulator;

  public SimAlgaeByeByeRollerIO() {
    super();
    algeaByeByeRollerSimulator = new AlgeaByeByeRollerSimulator((removerMotor));
  }

  @Override
  public void updateInputs(AlgaeByeByeRollerInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algeaByeByeRollerSimulator.simulationPeriodic();
    }
  }
}
