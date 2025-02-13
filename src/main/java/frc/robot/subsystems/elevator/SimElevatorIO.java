package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class SimElevatorIO extends RealElevatorIO {
  private final ElevatorSimulator elevatorSimulator;

  public SimElevatorIO() {
    super();
    this.elevatorSimulator = new ElevatorSimulator(elevatorMotor.getNeoMotor());
  }

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      elevatorSimulator.stepSimulation();
    }
  }
}
