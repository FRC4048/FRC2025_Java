package frc.robot.subsystems.elevator;

import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

public class SimElevatorIO extends RealElevatorIO {
  private final ElevatorSimulator elevatorSimulator;

  public SimElevatorIO(LoggedMechanismLigament2d elevatorLigament) {
    super();
    this.elevatorSimulator = new ElevatorSimulator(elevatorMotor.getNeoMotor(), elevatorLigament);
  }

  @Override
  public void updateInputs(PidMotorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      elevatorSimulator.stepSimulation();
    }
  }
}
