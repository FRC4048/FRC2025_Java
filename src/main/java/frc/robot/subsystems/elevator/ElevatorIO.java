package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public interface ElevatorIO extends LoggableIO<BuildableFolderMotorInputs<SparkMax>> {
  void setSpeed(double spd);

  void stopMotor();

  void resetEncoder();
}
