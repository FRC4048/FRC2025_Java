package frc.robot.subsystems.hihiroller;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

public class SimHihiRollerIO extends RealHihiRollerIO {
  private final MotorSimulator hihiRollerSimulator;

  public SimHihiRollerIO(LoggedMechanismLigament2d ligament) {
    super();
    hihiRollerSimulator = new MotorSimulator(hihiRollerMotor, ligament);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiRollerSimulator.stepSimulation();
    }
  }
}
