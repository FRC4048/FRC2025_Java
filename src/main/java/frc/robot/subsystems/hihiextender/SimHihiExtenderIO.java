package frc.robot.subsystems.hihiextender;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class SimHihiExtenderIO extends RealHihiExtenderIO {
  private final HihiExtenderSimulator hihiExtenderSimulator;

  public SimHihiExtenderIO() {
    super();
    hihiExtenderSimulator = new HihiExtenderSimulator(extenderMotor.getNeoMotor());
  }

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    super.updateInputs(inputs);
    if (Constants.currentMode == Constants.Mode.SIM) {
      hihiExtenderSimulator.simulationPeriodic();
    }
  }
}
