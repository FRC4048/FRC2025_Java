package frc.robot.subsystems.algaebyebyeroller;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.motor.MotorSimulator;

public class SimAlgaeByeByeRollerIO extends RealAlgaeByeByeRollerIO {
  private final MotorSimulator algaeByeByeRollerSimulator;

  public SimAlgaeByeByeRollerIO() {
    super();
    algaeByeByeRollerSimulator = new MotorSimulator(removerMotor);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algaeByeByeRollerSimulator.stepSimulation();
      SmartDashboard.putNumber("Algae Roller Encoder", removerMotor.getEncoder().getPosition());
    }
  }
}
