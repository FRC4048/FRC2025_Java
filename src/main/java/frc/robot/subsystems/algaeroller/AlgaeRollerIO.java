package frc.robot.subsystems.algaeroller;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeRollerIO extends LoggableIO<AlgaeRollerInputs> {
  void setRollerSpeed(double speed);

  void setTiltSpeed(double speed);

  void stopRollerMotor();

  void stopAngleMotor();
}
