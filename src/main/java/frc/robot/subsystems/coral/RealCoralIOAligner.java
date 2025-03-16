package frc.robot.subsystems.coral;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagSparkMaxEncoder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

public class RealCoralIOAligner implements CoralIOAligner {
  protected final SparkMax alignerMotor;
  private final SparkMaxInputProvider inputProvider;

  public RealCoralIOAligner() {
    alignerMotor =
        new SparkMax(Constants.SHOOTER_MOTOR_ALIGNER_ID, SparkLowLevel.MotorType.kBrushless);
    this.inputProvider = new SparkMaxInputProvider(alignerMotor);
    configureMotor();
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxEncoder(
                "Coral Aligner", "Encoder", Constants.CORAL_ALIGNER_DIAGS_ENCODER, alignerMotor));
  }

  private void configureMotor() {
    SparkMaxConfig alignerConfig = new SparkMaxConfig();
    alignerConfig.smartCurrentLimit(Constants.NEO_CURRENT_LIMIT);
    alignerMotor.configure(
        alignerConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setAlignerSpeed(double speed) {
    alignerMotor.set(speed);
  }

  @Override
  public void stopAligner() {
    alignerMotor.stopMotor();
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
