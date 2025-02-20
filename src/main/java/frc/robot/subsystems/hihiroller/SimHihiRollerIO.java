package frc.robot.subsystems.hihiroller;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.simulation.IntakeSimulator;

public class SimHihiRollerIO extends RealHihiRollerIO {
  private final IntakeSimulator hihiRollerSimulator;

  public SimHihiRollerIO() {
    super();
    hihiRollerSimulator = new IntakeSimulator(hihiRollerMotor);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiRollerSimulator.stepSimulation();
    }
  }
}
