/*package frc.robot.subsystems.coral;
package frc.robot.subsystems.coral;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.constants.Constants;

public class RealCoralIO implements CoralIO {
  private final WPI_TalonSRX shooterMotor1; // TODO: change later to whatever
  private final WPI_TalonSRX shooterMotor2; // TODO: change later to whatever
  private final WPI_TalonSRX shooterTiltMotor; // TODO: change later to whatever

  public RealCoralIO() {
    this.shooterMotor1 = new WPI_TalonSRX(Constants.SHOOTER_MOTOR_1_ID);
    this.shooterMotor2 = new WPI_TalonSRX(Constants.SHOOTER_MOTOR_2_ID);
    this.shooterTiltMotor = new WPI_TalonSRX(Constants.SHOOTER_TILT_MOTOR_ID);
    configureMotor();
    resetTiltEncoder();
  }

  private void configureMotor() {
    this.shooterMotor1.setNeutralMode(NeutralMode.Brake);
    this.shooterMotor2.setNeutralMode(NeutralMode.Brake);
    this.shooterTiltMotor.setNeutralMode(NeutralMode.Brake);
    this.shooterTiltMotor.configForwardLimitSwitchSource(
        LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    this.shooterTiltMotor.configReverseLimitSwitchSource(
        LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
  }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotor1.set(speed);
    this.shooterMotor2.set(speed);
  }

  @Override
  public void setTiltAngularVelocity(double angleSpeed) {
    this.shooterTiltMotor.set(angleSpeed);
  }

  @Override
  public void stopShooterMotors() {
    this.shooterMotor1.set(0);
    this.shooterMotor2.set(0);
  }

  @Override
  public void stopTiltMotors() {
    this.shooterTiltMotor.set(0);
  }

  @Override
  public void resetTiltEncoder() {
    this.shooterTiltMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void updateInputs(CoralInputs inputs) {
    inputs.shooterSpeed = shooterMotor1.get();
    inputs.fwdTripped = shooterTiltMotor.getSensorCollection().isFwdLimitSwitchClosed();
    inputs.revTripped = shooterTiltMotor.getSensorCollection().isRevLimitSwitchClosed();
    inputs.angleSpeed = shooterTiltMotor.get();
  }
}*/
