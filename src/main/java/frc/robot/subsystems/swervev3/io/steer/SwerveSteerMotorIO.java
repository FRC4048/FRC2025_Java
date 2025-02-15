package frc.robot.subsystems.swervev3.io.steer;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface SwerveSteerMotorIO extends LoggableIO<MotorInputs> {
  void setSteerVoltage(double volts);

  void resetEncoder();
}
