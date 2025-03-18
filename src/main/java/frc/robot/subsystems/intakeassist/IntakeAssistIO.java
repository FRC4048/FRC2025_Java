package frc.robot.subsystems.intakeassist;

import frc.robot.utils.logging.LoggableIO;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;

public interface IntakeAssistIO extends LoggableIO<MotorInputs> {
  void setSpeed(double speed);

  void stopMotors();

  void resetIntakeAssistEncoder();
}
