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

public class RealCoralIOLeader implements CoralIOLeader {
  protected final SparkMax shooterMotorLeader;
  private final SparkMaxInputProvider initProvider;

  public RealCoralIOLeader() {
    shooterMotorLeader =
        new SparkMax(Constants.SHOOTER_MOTOR_LEADER_ID, SparkLowLevel.MotorType.kBrushless);
    this.initProvider = new SparkMaxInputProvider(shooterMotorLeader);
    configureMotor();
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "Coral",
                "LeaderForward",
                shooterMotorLeader,
                DiagSparkMaxSwitch.Direction.FORWARD));
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxSwitch(
                "Coral",
                "LeaderReverse",
                shooterMotorLeader,
                DiagSparkMaxSwitch.Direction.REVERSE));
  }

  private void configureMotor() {
    SparkMaxConfig coralConfig = new SparkMaxConfig();
    coralConfig.apply(coralConfig.limitSwitch.forwardLimitSwitchEnabled(true));
    coralConfig.idleMode(IdleMode.kBrake);
    coralConfig.smartCurrentLimit(Constants.NEO_CURRENT_LIMIT);
    shooterMotorLeader.configure(
        coralConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setShooterSpeed(double speed) {
    this.shooterMotorLeader.set(speed);
  }

  @Override
  public void stopShooterMotors() {
    this.shooterMotorLeader.set(0);
  }

  @Override
  public void enableOrDisableLimitSwitch(boolean state) {
    SparkMaxConfig coralConfigMotorLeader = new SparkMaxConfig();
    coralConfigMotorLeader.apply(
        coralConfigMotorLeader.limitSwitch.forwardLimitSwitchEnabled(state));

    shooterMotorLeader.configure(
        coralConfigMotorLeader,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  public void setIdleMode(IdleMode mode) {
    SparkMaxConfig coralConfigMotor = new SparkMaxConfig();
    coralConfigMotor.idleMode(mode);
    shooterMotorLeader.configure(
        coralConfigMotor,
        SparkBase.ResetMode.kNoResetSafeParameters,
        SparkBase.PersistMode.kNoPersistParameters);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(initProvider);
    if (Constants.COMMAND_DEBUG) {
      SmartShuffleboard.put(
          "coral", "ForwardTrippedLeader", shooterMotorLeader.getForwardLimitSwitch().isPressed());
    }
  }
}
