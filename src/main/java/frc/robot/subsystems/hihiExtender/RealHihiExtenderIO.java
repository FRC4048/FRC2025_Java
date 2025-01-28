package frc.robot.subsystems.hihiExtender;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.constants.Constants;

public class RealHihiExtenderIO implements HihiExtenderIO {
  private final WPI_TalonSRX extenderMotor; // TODO: change later to whatever

  public RealHihiExtenderIO() {
    this.extenderMotor = new WPI_TalonSRX(Constants.ALGAE_EXTENDER_MOTOR_ID);
    configureMotor();
    resetExtenderEncoder();
  }

  private void configureMotor() {
    this.extenderMotor.setNeutralMode(NeutralMode.Brake);
    this.extenderMotor.configForwardLimitSwitchSource(
        LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    this.extenderMotor.configReverseLimitSwitchSource(
        LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
  }

  @Override
  public void stopHihiExtenderMotor() {
    this.extenderMotor.set(0);
  }

  @Override
  public void setHihiExtenderSpeed(double speed) {
    this.extenderMotor.set(speed);
  }

  @Override
  public void resetExtenderEncoder() {
    this.extenderMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void updateInputs(HihiExtenderInputs inputs) {
    inputs.hihiExtenderEncoderPos = extenderMotor.get();
    inputs.revTripped = extenderMotor.getSensorCollection().isFwdLimitSwitchClosed();
    inputs.fwdTripped = extenderMotor.getSensorCollection().isRevLimitSwitchClosed();
  }
}
