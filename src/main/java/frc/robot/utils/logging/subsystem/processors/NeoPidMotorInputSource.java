package frc.robot.utils.logging.subsystem.processors;

import frc.robot.utils.motor.NeoPidMotor;
import java.util.function.Function;

public class NeoPidMotorInputSource
    implements PidMotorInputSource<NeoPidMotor>, MotorInputSource<NeoPidMotor> {

  @Override
  public Function<NeoPidMotor, Double> setpointFromSource() {
    return NeoPidMotor::getPidPosition;
  }

  @Override
  public Function<NeoPidMotor, Double> currentFromSource() {
    return neoPidMotor -> neoPidMotor.getNeoMotor().getOutputCurrent();
  }

  @Override
  public Function<NeoPidMotor, Double> motorTemperatureFromSource() {
    return neoPidMotor -> neoPidMotor.getNeoMotor().getMotorTemperature();
  }

  @Override
  public Function<NeoPidMotor, Double> encoderPositionFromSource() {
    return neoPidMotor -> neoPidMotor.getNeoMotor().getEncoder().getPosition();
  }

  @Override
  public Function<NeoPidMotor, Double> encoderVelocityFromSource() {
    return neoPidMotor -> neoPidMotor.getNeoMotor().getEncoder().getVelocity();
  }

  @Override
  public Function<NeoPidMotor, Boolean> fwdLimitFromSource() {
    return neoPidMotor -> neoPidMotor.getNeoMotor().getForwardLimitSwitch().isPressed();
  }

  @Override
  public Function<NeoPidMotor, Boolean> revLimitFromSource() {
    return neoPidMotor -> neoPidMotor.getNeoMotor().getReverseLimitSwitch().isPressed();
  }
}
