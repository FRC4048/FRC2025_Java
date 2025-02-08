package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedMotorInputs;
import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeTiltIO extends LoggableIO<BuildableKeyedMotorInputs<SparkMax>> {
  void setSpeed(double speed);

  void stopMotors();

  void resetEncoder();
}
