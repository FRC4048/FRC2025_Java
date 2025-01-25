package frc.robot.subsystems.algaeRoller;

import frc.robot.utils.logging.LoggableIO;

public interface AlgaeRollerIO extends LoggableIO<AlgaeRollerInputs> {
  void setSpeedRoller(double speed);

  void setSpeedAngle(double speed);

  void stopRollerMotor();

  void stopAngleMotor();
}
