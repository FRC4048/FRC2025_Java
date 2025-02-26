package frc.robot.subsystems.swervev3.io.drive;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface SwerveDriveMotorIO extends LoggableIO<MotorInputs> {
  void setDriveVoltage(double volts);

  void resetEncoder();

  void updateConfig(double closedLoopRampRate, double secondaryCurrentLimit, int smartCurrentLimit);
}
