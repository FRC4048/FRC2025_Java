package frc.robot.subsystems.CoralAngle;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

public class RealCoralAngleIO implements CoralAngleIO {
  private final SparkMax shooterTiltMotor; // TODO: change later to whatever

  public RealCoralAngleIO() {
    shooterTiltMotor = new SparkMax(Constants.SHOOTER_TILT_MOTOR_ID, SparkMax.MotorType.kBrushless);
    configureMotor();
    resetTiltEncoder();
  }

  private void configureMotor() {
    SparkMaxConfig coralConfig = new SparkMaxConfig();
    coralConfig.idleMode(IdleMode.kBrake);
    shooterTiltMotor.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setTiltAngularVelocity(double angleSpeed) {
    this.shooterTiltMotor.set(angleSpeed);
  }

  @Override
  public void stopTiltMotors() {
    this.shooterTiltMotor.set(0);
  }

  @Override
  public void resetTiltEncoder() {
    this.shooterTiltMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(CoralAngleInput inputs) {
    inputs.fwdTripped = shooterTiltMotor.getForwardLimitSwitch().isPressed();
    inputs.revTripped = shooterTiltMotor.getReverseLimitSwitch().isPressed();
  }
}
