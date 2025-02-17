package frc.robot.subsystems.algaebyebyetilt;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public class SimAlgaeByeByeTiltIO extends RealAlgaeByeByeTiltIO {
  private final AlgaeByeByeTiltSimulator algaeByeByeTiltSimulator;

  public SimAlgaeByeByeTiltIO() {
    super();
    algaeByeByeTiltSimulator = new AlgaeByeByeTiltSimulator(removerTiltMotor);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algaeByeByeTiltSimulator.simulationPeriodic();
    }
  }
}
