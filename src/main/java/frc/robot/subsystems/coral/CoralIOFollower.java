package frc.robot.subsystems.coral;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface CoralIOFollower extends LoggableIO<MotorInputs> {

  void setIdleMode(IdleMode mode);
}
