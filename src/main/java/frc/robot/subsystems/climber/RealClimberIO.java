// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;
import frc.robot.utils.logging.subsystem.inputs.MotorInputs;
import frc.robot.utils.logging.subsystem.providers.SparkMaxInputProvider;

/** Add your docs here. */
public class RealClimberIO implements ClimberIO {
  protected final SparkMax climberMotor;
  private final SparkMaxInputProvider inputProvider;

  public RealClimberIO() {
    this.climberMotor = new SparkMax(Constants.CLIMBER_MOTOR_ID, SparkMax.MotorType.kBrushless);
    inputProvider = new SparkMaxInputProvider(climberMotor);
    configureMotor();
  }

  public void configureMotor() {
    SparkMaxConfig climberConfig = new SparkMaxConfig();
    climberConfig.idleMode(IdleMode.kBrake);
    climberMotor.configure(
        climberConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void setClimberSpeed(double speed) {
    climberMotor.set(speed);
  }

  @Override
  public void stopClimber() {
    climberMotor.set(0);
  }

  public void resetClimberEncoder() {
    climberMotor.getEncoder().setPosition(0);
  }

  @Override
  public void updateInputs(MotorInputs inputs) {
    inputs.process(inputProvider);
  }
}
