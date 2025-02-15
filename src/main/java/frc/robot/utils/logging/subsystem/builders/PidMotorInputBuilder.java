package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.PidMotorInputs;

public class PidMotorInputBuilder<T extends PidMotorInputBuilder<T>> extends MotorInputBuilder<T> {
  private boolean logPidSetpoint;

  public PidMotorInputBuilder(String folder) {
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

  public boolean isLogPidSetpoint() {
    return logPidSetpoint;
  }
}
