package frc.robot.subsystems.algaebyebyeroller;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

public class SimAlgaeByeByeRollerIO extends RealAlgaeByeByeRollerIO {
  private final MotorSimulator algaeByeByeRollerSimulator;

  public SimAlgaeByeByeRollerIO(LoggedMechanismLigament2d algaeByeByeRollerLigament) {
    super();
    algaeByeByeRollerSimulator = new MotorSimulator(removerMotor, algaeByeByeRollerLigament);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algaeByeByeRollerSimulator.stepSimulation();
    }
  }
}
