package frc.robot.utils.logging.subsystem.providers;

import frc.robot.utils.motor.NeoPidMotor;

public class NeoPidMotorInputProvider extends SparkMaxInputProvider implements PidMotorInputProvider {

  private final NeoPidMotor neoPidMotor;

  public NeoPidMotorInputProvider(NeoPidMotor neoPidMotor) {
    super(neoPidMotor.getNeoMotor());
    this.neoPidMotor = neoPidMotor;
  }

  @Override
  public double getPidSetpoint() {
    return neoPidMotor.getPidPosition();
  }
}
