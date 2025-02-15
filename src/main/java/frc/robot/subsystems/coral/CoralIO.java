package frc.robot.subsystems.coral;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface CoralIO extends LoggableIO<MotorInputs> {
  void setShooterSpeed(double speed);

  void stopShooterMotors();

  void enableOrDisableLimitSwitch(boolean state);

  void SetIdleMode(IdleMode mode);
}
