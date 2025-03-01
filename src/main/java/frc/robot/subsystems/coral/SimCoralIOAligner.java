package frc.robot.subsystems.coral;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;

public class SimCoralIOAligner extends RealCoralIOAligner {
  private final MotorSimulator alignerSimulator;

  public SimCoralIOAligner() {
    super();
    alignerSimulator = new MotorSimulator(alignerMotor);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      alignerSimulator.stepSimulation();
    }
  }
}
