package frc.robot.subsystems.algaebyebyeroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public interface AlgaeByeByeRollerIO extends LoggableIO<BuildableFolderMotorInputs<SparkMax>> {
  void setSpeed(double speed);

  void stopMotors();
}
