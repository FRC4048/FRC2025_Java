// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intakeassist;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.utils.diag.DiagSparkMaxEncoder;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

/** Add your docs here. */
public class RealIntakeAssistIO implements IntakeAssistIO {
  protected final SparkMax intakeAssistMotor;
  private final SparkMaxInputProvider inputProvider;

  public RealIntakeAssistIO() {
    this.intakeAssistMotor =
        new SparkMax(Constants.INTAKE_ASSIST_MOTOR_ID, SparkMax.MotorType.kBrushless);
    inputProvider = new SparkMaxInputProvider(intakeAssistMotor);
    configureMotor();
    Robot.getDiagnostics()
        .addDiagnosable(
            new DiagSparkMaxEncoder(
                "IntakeAssist",
                "Encoder",
                Constants.INTAKE_ASSIST_DIAGS_ENCODER,
                intakeAssistMotor));
  }

  private void configureMotor() {
    SparkMaxConfig intakeAssistMotorConfig = new SparkMaxConfig();
    intakeAssistMotorConfig.idleMode(IdleMode.kBrake);
    intakeAssistMotorConfig.smartCurrentLimit(Constants.NEO_CURRENT_LIMIT);
    intakeAssistMotor.configure(
        intakeAssistMotorConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setSpeed(double speed) {
    intakeAssistMotor.set(speed);
  }

  @Override
  public void stopMotors() {
    intakeAssistMotor.set(0);
  }

  public void resetIntakeAssistEncoder() {
    intakeAssistMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
