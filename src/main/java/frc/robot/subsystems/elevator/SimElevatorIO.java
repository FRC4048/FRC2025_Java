package frc.robot.subsystems.elevator;

import frc.robot.constants.Constants;
import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;

public class SimElevatorIO extends RealElevatorIO {
  private final ElevatorSimulator elevatorSimulator;

  public SimElevatorIO() {
    super();
    this.elevatorSimulator = new ElevatorSimulator(elevatorMotor);
  }

  @Override
  public void updateInputs(LimitedEncodedMotorInput inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      elevatorSimulator.simulationPeriodic();
    }
  }
}
