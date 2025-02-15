package frc.robot.utils.logging.subsystem.providers;

public interface PidMotorInputProvider extends MotorInputProvider {
  double getPidSetpoint();
}
