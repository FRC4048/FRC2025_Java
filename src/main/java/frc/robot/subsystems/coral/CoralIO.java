package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public interface CoralIO extends LoggableIO<BuildableFolderMotorInputs<SparkMax>> {
  void setShooterSpeed(double speed);

  void stopShooterMotors();

  void enableOrDisableLimitSwitch(boolean state);

  void breakMode(IdleMode mode);
}
