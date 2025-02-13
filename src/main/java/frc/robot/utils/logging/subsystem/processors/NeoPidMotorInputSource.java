package frc.robot.utils.logging.subsystem.processors;

import frc.robot.utils.motor.NeoPidMotor;

public class NeoPidMotorInputSource extends SparkMaxInputSource implements PidMotorInputSource {

  private final NeoPidMotor neoPidMotor;

  public NeoPidMotorInputSource(NeoPidMotor neoPidMotor) {
    super(neoPidMotor.getNeoMotor());
    this.neoPidMotor = neoPidMotor;
  }

  @Override
  public double getPidSetpoint() {
    return neoPidMotor.getPidPosition();
  }
}
