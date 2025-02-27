package frc.robot.subsystems.algaebyebyetilt;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.simulation.ArmParameters;
import frc.robot.utils.simulation.ArmSimulator;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

public class SimAlgaeByeByeTiltIO extends RealAlgaeByeByeTiltIO {
  private final ArmSimulator algaeByeByeRTiltSimulator;

  public SimAlgaeByeByeTiltIO(LoggedMechanismLigament2d algaeByeByeTiltLigament) {
    super();
    ArmParameters params = createParams();
    algaeByeByeRTiltSimulator = new ArmSimulator(removerTiltMotor, params, algaeByeByeTiltLigament);
  }

  private static ArmParameters createParams() {
    ArmParameters params = new ArmParameters();
    params.name = "ByeBye Tilt";
    params.armGearing = Constants.BYEBYE_TILT_GEARING;
    params.armInertia = Constants.BYEBYE_TILT_INERTIA;
    params.armLength = Constants.BYEBYE_TILT_LENGTH;
    params.armMinAngle = Constants.BYEBYE_TILT_MIN_ANGLE;
    params.armMaxAngle = Constants.BYEBYE_TILT_MAX_ANGLE;
    params.armSimulateGravity = Constants.BYEBYE_TILT_SIMULATE_GRAVITY;
    return params;
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algaeByeByeRTiltSimulator.stepSimulation();
    }
  }
}
