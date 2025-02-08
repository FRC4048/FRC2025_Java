package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedMotorInputs;
import frc.robot.utils.logging.LoggableIO;

public interface ElevatorIO extends LoggableIO<BuildableKeyedMotorInputs<SparkMax>> {
  void setSpeed(double spd);
}
