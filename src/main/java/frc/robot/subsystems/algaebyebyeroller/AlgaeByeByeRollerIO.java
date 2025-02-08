package frc.robot.subsystems.algaebyebyeroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedMotorInputs;
import frc.robot.utils.logging.LoggableIO;

public interface AlgaeByeByeRollerIO extends LoggableIO<BuildableKeyedMotorInputs<SparkMax>> {
  void setSpeed(double speed);

  void stopMotors();
}
