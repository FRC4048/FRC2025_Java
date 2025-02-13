package frc.robot.utils.logging.subsystem.builders;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.logging.subsystem.processors.InputSource;
import frc.robot.utils.logging.subsystem.processors.PidMotorInputSource;
import org.littletonrobotics.junction.LogTable;

/** Contains Inputs that could be logged for a Motor with a Pid */
public class PidMotorInputs extends MotorInputs {

  private Double pidSetpoint;

  public PidMotorInputs(Builder<?> builder) {
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
  public boolean process(InputSource inputSource) {
    boolean success = super.process(inputSource);

    if (success && inputSource instanceof PidMotorInputSource pidMotorInputSource) {
      if (pidSetpoint != null) {
        pidSetpoint = pidMotorInputSource.getPidSetpoint();
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

  public static class Builder<T extends Builder<T>> extends MotorInputs.Builder<T> {
    private boolean logPidSetpoint;

    public Builder(String folder) {
      super(folder);
    }

    @Override
    public PidMotorInputs build() {
      return new PidMotorInputs(this);
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
