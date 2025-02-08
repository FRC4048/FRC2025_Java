package frc.robot.subsystems.swervev3.io.steer;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public interface SwerveSteerMotorIO extends LoggableIO<BuildableFolderMotorInputs<SparkMax>> {
  void setSteerVoltage(double volts);

  void resetEncoder();
}
