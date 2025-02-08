package frc.robot.subsystems.elevator;

import frc.robot.utils.commoninputs.LimitedEncodedMotorInput;
import frc.robot.utils.logging.LoggableIO;

public interface ElevatorIO extends LoggableIO<LimitedEncodedMotorInput> {
  void setSpeed(double spd);
}
