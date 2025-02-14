package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class SimAlgaeByeByeTiltIO extends RealAlgaeByeByeTiltIO {
  private final AlgaeByeByeTiltSimulator algaeByeByeTiltSimulator;

  public SimAlgaeByeByeTiltIO() {
    super();
    algaeByeByeTiltSimulator = new AlgaeByeByeTiltSimulator(removerTiltMotor);
  }

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      algaeByeByeTiltSimulator.simulationPeriodic();
    }
  }
}
