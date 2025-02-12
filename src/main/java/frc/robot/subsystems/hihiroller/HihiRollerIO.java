package frc.robot.subsystems.hihiroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;

public interface HihiRollerIO extends LoggableIO<MotorInputs<SparkMax>> {
  void setRollerSpeed(double speed);

  void stopRollerMotor();
}
