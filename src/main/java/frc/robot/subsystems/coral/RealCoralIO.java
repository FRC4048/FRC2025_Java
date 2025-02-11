package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

public class RealCoralIO implements CoralIO {
  private final SparkMax shooterMotor1; // TODO: change later to whatever
  private final SparkMax shooterMotor2; // TODO: change later to whatever

  public RealCoralIO() {
    shooterMotor1 = new SparkMax(Constants.SHOOTER_MOTOR_1_ID, SparkMax.MotorType.kBrushless);
    shooterMotor2 = new SparkMax(Constants.SHOOTER_MOTOR_2_ID, SparkMax.MotorType.kBrushless);
    configureMotor();
  }

  private void configureMotor() {
    SparkMaxConfig coralConfigMotor1 = new SparkMaxConfig();
    SparkMaxConfig coralConfigMotor2 = new SparkMaxConfig();
    coralConfigMotor1.apply(coralConfigMotor1.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfigMotor1.apply(coralConfigMotor1.limitSwitch.reverseLimitSwitchEnabled(true));
    coralConfigMotor2.apply(coralConfigMotor1.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfigMotor2.apply(coralConfigMotor1.limitSwitch.reverseLimitSwitchEnabled(true));
    coralConfigMotor1.idleMode(IdleMode.kBrake);
    shooterMotor1.configure(
        coralConfigMotor1,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
    shooterMotor2.configure(
        coralConfigMotor2,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotor1.set(speed);
    this.shooterMotor2.set(speed);
  }

  @Override
  public void stopShooterMotors() {
    this.shooterMotor1.set(0);
    this.shooterMotor2.set(0);
  }

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {
    SparkMaxConfig coralConfigMotor1 = new SparkMaxConfig();
    coralConfigMotor1.apply(coralConfigMotor1.limitSwitch.forwardLimitSwitchEnabled(state));
    coralConfigMotor1.apply(coralConfigMotor1.limitSwitch.reverseLimitSwitchEnabled(state));
    shooterMotor1.configure(
        coralConfigMotor1,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
    shooterMotor2.configure(
        coralConfigMotor1,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  @Override
  public void updateInputs(CoralInputs inputs) {}
}
