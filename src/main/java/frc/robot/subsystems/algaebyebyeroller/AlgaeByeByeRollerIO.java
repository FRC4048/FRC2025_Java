package frc.robot.subsystems.algaebyebyeroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;
import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeRollerIO extends LoggableIO<BuildableKeyedInputs<SparkMax>> {
  void setSpeed(double speed);

  void stopMotors();
}
