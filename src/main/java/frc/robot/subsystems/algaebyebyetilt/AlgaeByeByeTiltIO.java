package frc.robot.subsystems.algaebyebyetilt;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface AlgaeByeByeTiltIO extends LoggableIO<MotorInputs> {
  void setSpeed(double speed);

  void stopMotors();

  void resetEncoder();
}
