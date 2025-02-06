package frc.robot.subsystems.hihiroller;

import frc.robot.utils.commoninputs.EncodedMotorInput;
import frc.robot.utils.logging.LoggableIO;

public interface HihiRollerIO extends LoggableIO<EncodedMotorInput> {
  void setRollerSpeed(double speed);

  void stopRollerMotor();
}
