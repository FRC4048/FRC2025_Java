package frc.robot.utils.logging.subsystem.builders;

import frc.robot.utils.logging.subsystem.inputs.DoubleInput;
import frc.robot.utils.logging.subsystem.processors.PidMotorInputProcessor;

public class PidMotorInputBuilder<R> extends MotorInputBuilder<R> {

  private DoubleInput<R> pidSetpoint;

  public PidMotorInputBuilder(String folder, PidMotorInputProcessor<R> inputProcessor) {
    super(folder, inputProcessor);
  }

  public MotorInputBuilder<R> pidSetpoint() {
    pidSetpoint =
        new DoubleInput<>(
            "pidSetpoint", ((PidMotorInputProcessor<R>) inputProcessor).setpointFromSource());
    return this;
  }

  @Override
  public BuildableFolderMotorInputs<R> build() {
    return new BuildableFolderPidMotorInputs<>(
        folder,
        encoderPosition,
        encoderVelocity,
        motorCurrent,
        motorTemperature,
        fwdLimit,
        revLimit,
        pidSetpoint);
  }

  @Override
  public MotorInputBuilder<R> addAll() {
    return ((PidMotorInputBuilder<R>) super.addAll()).pidSetpoint();
  }
}
