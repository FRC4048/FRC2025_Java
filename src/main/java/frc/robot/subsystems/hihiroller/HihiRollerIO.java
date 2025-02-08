package frc.robot.subsystems.hihiroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableKeyedMotorInputs;

public interface HihiRollerIO extends LoggableIO<BuildableKeyedMotorInputs<SparkMax>> {
  void setRollerSpeed(double speed);

  void stopRollerMotor();
}
