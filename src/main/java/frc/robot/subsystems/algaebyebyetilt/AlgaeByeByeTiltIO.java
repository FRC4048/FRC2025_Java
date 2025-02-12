package frc.robot.subsystems.algaebyebyetilt;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;

public interface AlgaeByeByeTiltIO extends LoggableIO<MotorInputs<SparkMax>> {
  void setSpeed(double speed);

  void stopMotors();

  void resetEncoder();
}
