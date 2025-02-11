package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

public class RealCoralIO implements CoralIO {
  private final SparkMax shooterMotorLeader; // TODO: change later to whatever
  private final SparkMax shooterMotorFollower; // TODO: change later to whatever

  public RealCoralIO() {
    shooterMotorLeader = new SparkMax(Constants.SHOOTER_MOTOR_1_ID, SparkMax.MotorType.kBrushless);
    shooterMotorFollower =
        new SparkMax(Constants.SHOOTER_MOTOR_2_ID, SparkMax.MotorType.kBrushless);
    configureMotor();
  }

  private void configureMotor() {
    SparkMaxConfig coralConfigMotorLeader = new SparkMaxConfig();
    SparkMaxConfig coralConfigMotorFollower = new SparkMaxConfig();
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.reverseLimitSwitchEnabled(true));
    coralConfigMotorFollower.apply(
        coralConfigMotorLeader.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfigMotorFollower.apply(
        coralConfigMotorLeader.limitSwitch.reverseLimitSwitchEnabled(true));
    coralConfigMotorLeader.idleMode(IdleMode.kBrake);
    shooterMotorLeader.configure(
        coralConfigMotorLeader,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
    shooterMotorFollower.configure(
        coralConfigMotorFollower,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotorLeader.set(speed);
    this.shooterMotorFollower.set(speed);
  }

  @Override
  public void stopShooterMotors() {
    this.shooterMotorLeader.set(0);
    this.shooterMotorFollower.set(0);
  }

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {
    SparkMaxConfig coralConfigMotorLeader = new SparkMaxConfig();
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.forwardLimitSwitchEnabled(state));
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.reverseLimitSwitchEnabled(state));
    shooterMotorLeader.configure(
        coralConfigMotorLeader,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  @Override
  public void updateInputs(CoralInputs inputs) {
    inputs.fwdTripped = shooterMotorLeader.getForwardLimitSwitch().isPressed();
    inputs.revTripped = shooterMotorLeader.getReverseLimitSwitch().isPressed();
  }
}
