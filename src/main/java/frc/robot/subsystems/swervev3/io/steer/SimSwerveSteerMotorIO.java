package frc.robot.subsystems.swervev3.io.steer;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.SteerMotorInputs;

public interface SimSwerveSteerMotorIO extends LoggableIO<SteerMotorInputs> {
  void setSteerVoltage(double voltage);

  void resetEncoder();
}
