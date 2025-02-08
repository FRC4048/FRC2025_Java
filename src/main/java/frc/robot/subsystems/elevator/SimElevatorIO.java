package frc.robot.subsystems.elevator;

import frc.robot.constants.Constants;

public class SimElevatorIO extends RealElevatorIO {
  private final ElevatorSimulator elevatorSimulator;

  public SimElevatorIO() {
    super();
    this.elevatorSimulator = new ElevatorSimulator(elevatorMotor);
  }

  @Override
  public void updateInputs(ElevatorInputs inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      elevatorSimulator.simulationPeriodic();
    }
  }
}
