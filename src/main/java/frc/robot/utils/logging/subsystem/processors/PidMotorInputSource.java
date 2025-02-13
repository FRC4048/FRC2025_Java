package frc.robot.utils.logging.subsystem.processors;

public interface PidMotorInputSource extends MotorInputSource {
  double getPidSetpoint();
}
