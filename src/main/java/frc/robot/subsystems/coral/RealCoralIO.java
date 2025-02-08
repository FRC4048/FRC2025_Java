package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

public class RealCoralIO implements CoralIO {
  private final SparkMax shooterMotor1; // TODO: change later to whatever
  private final SparkMax shooterMotor2; // TODO: change later to whatever
  private final SparkMax shooterTiltMotor; // TODO: change later to whatever
  private final SparkMaxConfig coralConfig;

  public RealCoralIO() {
    coralConfig = new SparkMaxConfig();
    shooterMotor1 = new SparkMax(Constants.SHOOTER_MOTOR_1_ID, SparkMax.MotorType.kBrushless);
    shooterMotor2 = new SparkMax(Constants.SHOOTER_MOTOR_2_ID, SparkMax.MotorType.kBrushless);
    shooterTiltMotor = new SparkMax(Constants.SHOOTER_TILT_MOTOR_ID, SparkMax.MotorType.kBrushless);
    configureMotor();
    resetTiltEncoder();
  }

  private void configureMotor() {
    coralConfig.idleMode(IdleMode.kBrake);
    shooterMotor1.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
    shooterMotor2.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
    shooterTiltMotor.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
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
    this.shooterTiltMotor.getEncoder().setPosition(0);
  }

  @Override
  public void toggleLimitSwitch(boolean state) {
    coralConfig.apply(coralConfig.limitSwitch.forwardLimitSwitchEnabled(state));
    coralConfig.apply(coralConfig.limitSwitch.reverseLimitSwitchEnabled(state));
  }

  @Override
  public void updateInputs(CoralInputs inputs) {
    inputs.fwdTripped = shooterTiltMotor.getForwardLimitSwitch().isPressed();
    inputs.revTripped = shooterTiltMotor.getReverseLimitSwitch().isPressed();
  }
}
