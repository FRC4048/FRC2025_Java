package frc.robot.subsystems.hihiroller;

import frc.robot.constants.Constants;
import frc.robot.utils.motor.MotorSimulator;

public class SimHihiRollerIO extends RealHihiRollerIO {
  private final MotorSimulator hihiRollerSimulator;

  public SimHihiRollerIO() {
    super();
    hihiRollerSimulator = new MotorSimulator(hihiRollerMotor);
  }

  @Override
  public void updateInputs(HihiRollerInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiRollerSimulator.simulationPeriodic();
    }
  }
}
