package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableKeyedMotorInputs;

public interface AlgaeByeByeTiltIO extends LoggableIO<BuildableKeyedMotorInputs<SparkMax>> {
  void setSpeed(double speed);

  void stopMotors();

  void resetEncoder();
}
