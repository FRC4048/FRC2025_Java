package frc.robot.utils.logging.subsystem;

public interface InputSource<T, R> {
  T fromSource(R source);
}
