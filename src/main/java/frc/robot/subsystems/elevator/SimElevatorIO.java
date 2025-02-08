package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;

public class SimElevatorIO extends RealElevatorIO {
  private final ElevatorSimulator elevatorSimulator;

  public SimElevatorIO() {
    super();
    this.elevatorSimulator = new ElevatorSimulator(elevatorMotor);
  }

  @Override
  public void updateInputs(BuildableKeyedInputs<SparkMax> inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      elevatorSimulator.simulationPeriodic();
    }
  }
}
