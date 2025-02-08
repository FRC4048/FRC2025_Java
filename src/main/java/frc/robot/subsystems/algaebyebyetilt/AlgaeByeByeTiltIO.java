package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;
import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeTiltIO extends LoggableIO<BuildableKeyedInputs<SparkMax>> {
  void setSpeed(double speed);

  void stopMotors();

  void resetEncoder();
}
