package frc.robot.subsystems.coral;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface CoralIOAligner extends LoggableIO<MotorInputs> {

  void setAlignerSpeed(double speed);
}
