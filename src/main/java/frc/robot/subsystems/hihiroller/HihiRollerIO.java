package frc.robot.subsystems.hihiroller;

import com.revrobotics.spark.SparkMax;
import frc.robot.utils.commoninputs.BuildableKeyedInputs;
import frc.robot.utils.logging.LoggableIO;

public interface HihiRollerIO extends LoggableIO<BuildableKeyedInputs<SparkMax>> {
  void setRollerSpeed(double speed);

  void stopRollerMotor();
}
