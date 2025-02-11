package frc.robot.utils.logging.subsystem.processors;

public interface PidMotorInputProcessor<R> extends MotorInputProcessor<R> {
  InputSource<Double, R> setpointFromSource();
}
