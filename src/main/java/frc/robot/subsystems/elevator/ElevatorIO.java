package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;
import frc.robot.utils.logging.LoggableIO;

public interface ElevatorIO extends LoggableIO<BuildableKeyedInputs<SparkMax>> {
  void setSpeed(double spd);
}
