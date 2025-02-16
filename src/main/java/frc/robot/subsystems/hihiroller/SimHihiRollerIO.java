package frc.robot.subsystems.hihiroller;

import frc.robot.constants.Constants;
import frc.robot.subsystems.RobotVisualizer;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;

public class SimHihiRollerIO extends RealHihiRollerIO {
  private final MotorSimulator hihiRollerSimulator;

  public SimHihiRollerIO() {
    super();
    hihiRollerSimulator = new MotorSimulator(hihiRollerMotor);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiRollerSimulator.stepSimulation();
      if (hihiRollerSimulator.getEncoder().getVelocity() > 0) {
        RobotVisualizer.getInstance().spinAlgaeHiHiRollerAngle();
      }
    }
  }
}
