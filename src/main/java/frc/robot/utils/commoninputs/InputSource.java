package frc.robot.utils.commoninputs;

public interface InputSource<T, R> {
  T fromSource(R source);
}
