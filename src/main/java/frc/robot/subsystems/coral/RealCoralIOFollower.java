package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class RealCoralIOFollower implements CoralIO {
  private final SparkMax shooterMotor;
  private final SparkMaxInputProvider inputProvider;

  public RealCoralIOFollower() {
    shooterMotor =
        new SparkMax(Constants.SHOOTER_MOTOR_FOLLOWER_ID, SparkLowLevel.MotorType.kBrushless);
    inputProvider = new SparkMaxInputProvider(shooterMotor);
    configureMotor();
  }

  private void configureMotor() {
    SparkMaxConfig coralConfig = new SparkMaxConfig();
    coralConfig.apply(coralConfig.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfig.idleMode(IdleMode.kBrake);
    coralConfig.follow(Constants.SHOOTER_MOTOR_LEADER_ID);
    shooterMotor.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotor.set(speed);
  }

  @Override
  public void stopShooterMotors() {}

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {}

  public void setIdleMode(IdleMode mode) {
    SparkMaxConfig coralConfigMotor = new SparkMaxConfig();
    coralConfigMotor.idleMode(mode);
    shooterMotor.configure(
        coralConfigMotor,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
    if (Constants.COMMAND_DEBUG) {
      SmartShuffleboard.put(
          "coral", "ForwardTrippedFollower", shooterMotor.getForwardLimitSwitch().isPressed());
      SmartShuffleboard.put("coral", "IsFollower", shooterMotor.isFollower());
    }
  }
}
