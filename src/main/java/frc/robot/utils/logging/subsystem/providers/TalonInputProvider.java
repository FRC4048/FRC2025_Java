package frc.robot.utils.logging.subsystem.providers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/** Talon Input Builder implementation */
public class TalonInputProvider implements MotorInputProvider {

  private final WPI_TalonSRX talon;

  public TalonInputProvider(WPI_TalonSRX talon) {
    this.talon = talon;
  }

  @Override
  public double getMotorCurrent() {
    return talon.getStatorCurrent();
  }

  @Override
  public double getMotorTemperature() {
    return talon.getTemperature();
  }

  @Override
  public double getEncoderPosition() {
    return talon.getSelectedSensorPosition();
  }

  @Override
  public double getEncoderVelocity() {
    return talon.getSelectedSensorVelocity();
  }

  @Override
  public boolean getFwdLimit() {
    return talon.isFwdLimitSwitchClosed() == 1;
  }

  @Override
  public boolean getRevLimit() {
    return talon.isRevLimitSwitchClosed() == 1;
  }

  @Override
  public double getAppliedOutput() {
    return talon.getMotorOutputVoltage();
  }
}
