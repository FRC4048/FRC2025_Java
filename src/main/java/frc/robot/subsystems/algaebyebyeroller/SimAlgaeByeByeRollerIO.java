package frc.robot.subsystems.algaebyebyeroller;

import frc.robot.constants.Constants;

public class SimAlgaeByeByeRollerIO extends RealAlgaeByeByeRollerIO {
  private final AlgaeByeByeRollerSimulator algaeByeByeRollerSimulator;

  public SimAlgaeByeByeRollerIO() {
    super();
    algaeByeByeRollerSimulator = new AlgaeByeByeRollerSimulator(removerMotor);
  }

  @Override
  public void updateInputs(AlgaeByeByeRollerInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algaeByeByeRollerSimulator.simulationPeriodic();
    }
  }
}
