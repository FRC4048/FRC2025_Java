package frc.robot.utils.logging.subsystem.builders;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.logging.subsystem.processors.PidMotorInputSource;
import org.littletonrobotics.junction.LogTable;

/** Contains Inputs that could be logged for a Motor with a Pid */
public class PidMotorInputs<R> extends MotorInputs<R> {
  private Double pidSetpoint;

  public PidMotorInputs(Builder<R, ?> builder) {
    super(builder);
    this.pidSetpoint = builder.logPidSetpoint ? 0.0 : null;
  }

  @Override
  public void toLog(LogTable table) {
    super.toLog(table);
    if (pidSetpoint != null) {
      table.put("pidSetpoint", pidSetpoint);
    }
  }

  @Override
  public void fromLog(LogTable table) {
    super.fromLog(table);
    if (pidSetpoint != null) {
      pidSetpoint = table.get("pidSetpoint", pidSetpoint);
    }
  }

  @Override
  public boolean process(R source) {
    boolean success = super.process(source);
    if (success && inputSource instanceof PidMotorInputSource<R> pidInputSource) {
      if (pidSetpoint != null) {
        pidSetpoint = pidInputSource.setpointFromSource().apply(source);
      }
      return true;
    } else {
      DriverStation.reportError("InputSource must be of type PidMotorInputSource", true);
      return false;
    }
  }

  public Double getPidSetpoint() {
    return pidSetpoint;
  }

  public static class Builder<R, T extends Builder<R, T>> extends MotorInputs.Builder<R, T> {
    private boolean logPidSetpoint;

    public Builder(String folder, PidMotorInputSource<R> inputProcessor) {
      super(folder, inputProcessor);
    }

    @Override
    public PidMotorInputs<R> build() {
      return new PidMotorInputs<>(this);
    }

    @Override
    public T addAll() {
      return super.addAll().pidSetpoint();
    }

    @Override
    public T reset() {
      super.reset();
      logPidSetpoint = false;
      return self();
    }

    public T pidSetpoint() {
      logPidSetpoint = true;
      return self();
    }
  }
}
