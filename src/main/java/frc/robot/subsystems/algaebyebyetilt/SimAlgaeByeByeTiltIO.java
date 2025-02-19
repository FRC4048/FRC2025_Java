package frc.robot.subsystems.algaebyebyetilt;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.simulation.ArmParameters;
import frc.robot.utils.simulation.ArmSimulator;

public class SimAlgaeByeByeTiltIO extends RealAlgaeByeByeTiltIO {
  private final ArmSimulator algaeByeByeTiltSimulator;
  private ArmParameters params;

  public SimAlgaeByeByeTiltIO() {
    super();
    params = new ArmParameters();
    params.name = "Algae Bye Bye Tilt";
    params.armGearing = Constants.BYEBYE_GEARING;
    params.armInertia = Constants.BYEBYE_INERTIA;
    params.armLength = Constants.BYEBYE_LENGTH;
    params.armMinAngle = Constants.BYEBYE_MIN_ANGLE;
    params.armMaxAngle = Constants.BYEBYE_MAX_ANGLE;
    params.armSimulateGravity = Constants.BYE_BYE_SIMULATE_GRAVITY;
    algaeByeByeTiltSimulator = new ArmSimulator(removerTiltMotor, params);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algaeByeByeTiltSimulator.stepSimulation();
    }
  }
}
