package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagSparkMaxSwitch;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;
import frc.robot.utils.shuffleboard.SmartShuffleboard;

public class RealCoralIOFollower implements CoralIOFollower {
  protected final SparkMax shooterMotorFollower;
  private final SparkMaxInputProvider inputProvider;

  public RealCoralIOFollower() {
    shooterMotorFollower =
        new SparkMax(Constants.SHOOTER_MOTOR_FOLLOWER_ID, SparkLowLevel.MotorType.kBrushless);
    inputProvider = new SparkMaxInputProvider(shooterMotorFollower);
    configureMotor();
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "Coral",
                "FollowerForward",
                shooterMotorFollower,
                DiagSparkMaxSwitch.Direction.FORWARD));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "Coral",
                "FollowerReverse",
                shooterMotorFollower,
                DiagSparkMaxSwitch.Direction.REVERSE));
  }

  private void configureMotor() {
    SparkMaxConfig coralConfig = new SparkMaxConfig();
    coralConfig.apply(coralConfig.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfig.idleMode(IdleMode.kBrake);
    coralConfig.smartCurrentLimit(Constants.NEO_CURRENT_LIMIT);
    coralConfig.follow(Constants.SHOOTER_MOTOR_LEADER_ID, true);
    shooterMotorFollower.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void setIdleMode(IdleMode mode) {
    SparkMaxConfig coralConfigMotor = new SparkMaxConfig();
    coralConfigMotor.idleMode(mode);
    shooterMotorFollower.configure(
        coralConfigMotor,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
    if (Constants.COMMAND_DEBUG) {
      SmartShuffleboard.put(
          "coral",
          "ForwardTrippedFollower",
          shooterMotorFollower.getForwardLimitSwitch().isPressed());
    }
  }
}
