package frc.robot.utils.logging.subsystem;

/**
 * @param <T> Type of data to pull from hardware
 * @param <R> Type of hardware to pull from
 */
public interface InputSource<T, R> {
  T fromSource(R source);
}
