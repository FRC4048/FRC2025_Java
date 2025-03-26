package frc.robot.subsystems.coral;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

public class SimCoralIOAligner extends RealCoralIOAligner {
  private final MotorSimulator alignerSimulator;

  public SimCoralIOAligner(LoggedMechanismLigament2d ligament) {
    super();
    alignerSimulator = new MotorSimulator(alignerMotor, ligament);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      alignerSimulator.stepSimulation();
    }
  }
}
