package frc.robot.subsystems.algaebyebyeroller;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface AlgaeByeByeRollerIO extends LoggableIO<MotorInputs> {
  void setSpeed(double speed);

  void stopMotors();
}
