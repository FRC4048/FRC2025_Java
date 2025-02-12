package frc.robot.subsystems.swervev3.io.steer;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.MotorInputs;

public interface SwerveSteerMotorIO extends LoggableIO<MotorInputs<SparkMax>> {
  void setSteerVoltage(double volts);

  void resetEncoder();
}
