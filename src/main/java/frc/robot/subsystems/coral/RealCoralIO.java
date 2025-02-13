package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.builders.BuildableFolderMotorInputs;

public class RealCoralIO implements CoralIO {
  private final SparkMax shooterMotor; // TODO: change later to whatever // TODO: change later to whatever

  public RealCoralIO() {
    shooterMotor =
        new SparkMax(Constants.SHOOTER_MOTOR_LEADER, SparkMax.MotorType.kBrushless);
    configureMotor();
  }

  private void configureMotor() {
    SparkMaxConfig coralConfigMotorLeader = new SparkMaxConfig();
    SparkMaxConfig coralConfigMotorFollower = new SparkMaxConfig();
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.reverseLimitSwitchEnabled(true));
    coralConfigMotorLeader.idleMode(IdleMode.kBrake);
    shooterMotor.configure(
        coralConfigMotorLeader,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotor.set(speed);
  }

  @Override
  public void stopShooterMotors() {
    this.shooterMotor.set(0);
  }

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {
    SparkMaxConfig coralConfigMotorLeader = new SparkMaxConfig();
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.forwardLimitSwitchEnabled(state));
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.reverseLimitSwitchEnabled(state));
    shooterMotor.configure(
        coralConfigMotorLeader,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  @Override
  public void breakModeCoast(boolean coast) {
    SparkMaxConfig coralConfigMotorLeader = new SparkMaxConfig();
    if (coast) {
      coralConfigMotorLeader.idleMode(IdleMode.kCoast);
    } else {
      coralConfigMotorLeader.idleMode(IdleMode.kBrake);
    }
    shooterMotor.configure(
        coralConfigMotorLeader,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  @Override
  public void updateInputs(BuildableFolderMotorInputs<SparkMax> inputs) {
    inputs.process(shooterMotor);
  }
}
