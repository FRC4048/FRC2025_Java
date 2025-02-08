package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableKeyedMotorInputs;

public interface ElevatorIO extends LoggableIO<BuildableKeyedMotorInputs<SparkMax>> {
  void setSpeed(double spd);
}
